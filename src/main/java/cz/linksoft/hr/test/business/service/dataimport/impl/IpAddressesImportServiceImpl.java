package cz.linksoft.hr.test.business.service.dataimport.impl;

import cz.linksoft.hr.test.business.entity.CityEntity;
import cz.linksoft.hr.test.business.entity.CountryEntity;
import cz.linksoft.hr.test.business.entity.IpAddressRangeEntity;
import cz.linksoft.hr.test.business.entity.RegionEntity;
import cz.linksoft.hr.test.business.service.CityService;
import cz.linksoft.hr.test.business.service.CountryService;
import cz.linksoft.hr.test.business.service.IpAddressRangeService;
import cz.linksoft.hr.test.business.service.RegionService;
import cz.linksoft.hr.test.business.service.dataimport.IpAddressesImportService;
import cz.linksoft.hr.test.business.service.dataimport.exception.ImportRowFailedException;
import cz.linksoft.hr.test.core.ApplicationDataResourceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * @author jakubchalupa
 * @since 24.10.17
 */
@Service
public class IpAddressesImportServiceImpl implements IpAddressesImportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IpAddressesImportServiceImpl.class);

    protected final ApplicationDataResourceProvider applicationDataResourceProvider;
    protected final CountryService countryService;
    protected final RegionService regionService;
    protected final CityService cityService;
    protected final IpAddressRangeService ipAddressRangeService;

    @Autowired
    public IpAddressesImportServiceImpl(ApplicationDataResourceProvider applicationDataResourceProvider,
                                        CountryService countryService,
                                        RegionService regionService,
                                        CityService cityService,
                                        IpAddressRangeService ipAddressRangeService) {
        this.applicationDataResourceProvider = applicationDataResourceProvider;
        this.countryService = countryService;
        this.regionService = regionService;
        this.cityService = cityService;
        this.ipAddressRangeService = ipAddressRangeService;
    }

    /**
     * non-transactional on purpose
     */
    @Override
    public void importAllIpAddresses(@Nonnull Resource resource) throws Exception {
        LOGGER.debug("IMPORTING FILE " + resource.getFilename());
        final GZIPInputStream gzipInputStream = new GZIPInputStream(new BufferedInputStream(resource.getInputStream()));
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(gzipInputStream));

        // map Country code -> country -> region -> city
        // we need to mantain information to what region city belongs and to what country region belongs
        // reason is, that in dataset names of regions and cities can be duplicated, fe. Dolní Lhota could be in Jihočeský kraj as well as in Kraj Vysočina
        final Map<String, CountryWrapper> countriesMap = new HashMap<>();

        String line;
        ImportRow importRow;
        int rowNumber = 0;
        while ((line = bufferedReader.readLine()) != null) {
            LOGGER.debug("IMPORTING LINE " + ++rowNumber);

            try {
                LOGGER.debug(line);
                // split to columns, see https://stackoverflow.com/questions/15738918/splitting-a-csv-file-with-quotes-as-text-delimiter-using-string-split
                // if some enquoted value contains odd number of escaped quotes (for example "this \"is it" this regex will fail
                // question is, if its really valid value? for even number of escaped quotes in enquoted string this works just fine ("this \"is\" it").
                importRow = new ImportRow(fixElementsWrappingQuotes(line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)")));

                // if country was not already in dataset, then add it into map
                if (!countriesMap.containsKey(importRow.getCountryCode())) {
                    final CountryWrapper countryWrapper = new CountryWrapper(importRow.getCountryCode(), importRow.getCountryName());
                    countriesMap.put(importRow.getCountryCode(), countryWrapper);

                    // persist this new country
                    countryService.create(countryWrapper.getCountry());
                }

                final CountryWrapper countryWrapper = countriesMap.get(importRow.getCountryCode());
                final Map<String, RegionWrapper> regionsMap = countryWrapper.getRegionsMap();
                // if region was not already in dataset for given country, then add it into map
                if (!regionsMap.containsKey(importRow.getRegionName())) {
                    final RegionWrapper regionWrapper = new RegionWrapper(importRow.getRegionName());

                    countryWrapper.getCountry().addRegion(regionWrapper.getRegion());
                    regionsMap.put(importRow.getRegionName(), regionWrapper);

                    // persist this new region
                    regionService.create(regionWrapper.getRegion());
                }

                final RegionWrapper regionWrapper = regionsMap.get(importRow.getRegionName());
                final Map<String, CityEntity> citiesMap = regionWrapper.getCitiesMap();
                // if city was not already in dataset for given region, then add it into map
                if (!citiesMap.containsKey(importRow.getCityName())) {
                    final CityEntity cityEntity = new CityEntity();
                    cityEntity.setName(importRow.getCityName());
                    cityEntity.setLatitude(importRow.getCityLatitude());
                    cityEntity.setLongitude(importRow.getCityLongitude());

                    regionWrapper.getRegion().addCity(cityEntity);
                    citiesMap.put(importRow.getCityName(), cityEntity);

                    // persist this new city
                    cityService.create(cityEntity);
                }

                // add ip address range to given city in region in country
                final IpAddressRangeEntity ipAddressRangeEntity = new IpAddressRangeEntity();
                ipAddressRangeEntity.setRangeFrom(importRow.getIpFrom());
                ipAddressRangeEntity.setRangeTo(importRow.getIpTo());
                citiesMap.get(importRow.getCityName()).addIpAddressRange(ipAddressRangeEntity);

                // persist address range
                ipAddressRangeService.create(ipAddressRangeEntity);
            } catch (Exception e) {
                // if any error occured than audit the information about corrupted row
                throw new ImportRowFailedException(resource.getFilename(), rowNumber, e);
            }
        }

        LOGGER.debug("IMPORT DONE");
    }

    /**
     * will get rid of quotes surrounding the value
     * @param arr array of elements to fix
     * @return array of fixed elements
     */
    private static String[] fixElementsWrappingQuotes(String[] arr) {
        if (arr != null) {
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].length() > 1 && arr[i].startsWith("\"") && arr[i].endsWith("\"")) {
                    arr[i] = arr[i].substring(1, arr[i].length() - 1);
                }
            }
        }

        return arr;
    }

    /**
     * Entity representing one row of CSV file
     */
    private static class ImportRow {

        private final Long ipFrom;

        private final Long ipTo;

        private final String countryCode;

        private final String countryName;

        private final String regionName;

        private final String cityName;

        private final Double cityLatitude;

        private final Double cityLongitude;

        ImportRow(String[] row) {
            // check valid number of columns
            if (row == null || row.length != 8) {
                throw new IllegalArgumentException("Illegal number of columns.");
            }

            ipFrom = Long.parseLong(row[0]);
            ipTo = Long.parseLong(row[1]);
            countryCode = row[2];
            countryName = row[3];
            regionName = row[4];
            cityName = row[5];
            cityLatitude = Double.parseDouble(row[6]);
            cityLongitude = Double.parseDouble(row[7]);
        }

        Long getIpFrom() {
            return ipFrom;
        }

        Long getIpTo() {
            return ipTo;
        }

        String getCountryCode() {
            return countryCode;
        }

        String getCountryName() {
            return countryName;
        }

        String getRegionName() {
            return regionName;
        }

        String getCityName() {
            return cityName;
        }

        Double getCityLatitude() {
            return cityLatitude;
        }

        Double getCityLongitude() {
            return cityLongitude;
        }
    }

    /**
     * Simple wrapper around country for import purposes
     */
    private static class CountryWrapper {

        private final CountryEntity country;

        private final Map<String, RegionWrapper> regionsMap;

        CountryWrapper(String countryCode, String countryName) {
            country = new CountryEntity();
            country.setCode(countryCode);
            country.setName(countryName);

            regionsMap = new HashMap<>();
        }

        CountryEntity getCountry() {
            return country;
        }

        Map<String, RegionWrapper> getRegionsMap() {
            return regionsMap;
        }
    }

    /**
     * Simple wrapper over region for import purposes
     */
    private static class RegionWrapper {

        private final RegionEntity region;

        private final Map<String, CityEntity> citiesMap;

        RegionWrapper(String regionName) {
            region = new RegionEntity();
            region.setName(regionName);

            citiesMap = new HashMap<>();
        }

        RegionEntity getRegion() {
            return region;
        }

        Map<String, CityEntity> getCitiesMap() {
            return citiesMap;
        }

    }

}
