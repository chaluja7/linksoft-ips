package cz.linksoft.hr.test.business.service.dataimport;

import cz.linksoft.hr.test.IntegrationTest;
import cz.linksoft.hr.test.business.entity.CityEntity;
import cz.linksoft.hr.test.business.entity.CountryEntity;
import cz.linksoft.hr.test.business.entity.IpAddressRangeEntity;
import cz.linksoft.hr.test.business.entity.RegionEntity;
import cz.linksoft.hr.test.business.service.CityService;
import cz.linksoft.hr.test.business.service.CountryService;
import cz.linksoft.hr.test.business.service.IpAddressRangeService;
import cz.linksoft.hr.test.business.service.RegionService;
import cz.linksoft.hr.test.business.service.dataimport.exception.ImportRowFailedException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author cen83414
 * @since 26.10.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@IntegrationTest
public class IpAddressImportServiceIT {

    private static final int OK_IMPORT_NUMBER_OF_COUNTRIES = 1;
    private static final int OK_IMPORT_NUMBER_OF_REGIONS = 7;
    private static final int OK_IMPORT_NUMBER_OF_CITIES = 13;
    private static final int OK_IMPORT_NUMBER_OF_ROWS = 20;

    private static final String INCLUDED_COUNTRY_CODE = "SK";
    private static final String INCLUDED_REGION_NAME = "Nitriansky kraj";
    private static final String INCLUDED_CITY_NAME = "Zlate Moravce";
    private static final Long INCLUDED_IP_RANGE_FROM= 95564032L;

    @Autowired
    private IpAddressesImportService ipAddressesImportService;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private CountryService countryService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private CityService cityService;

    @Autowired
    private IpAddressRangeService ipAddressRangeService;

    @Test
    public void testImportOK() throws Exception {
        Assert.assertEquals(0, countryService.getAll().size());
        Assert.assertEquals(0, regionService.getAll().size());
        Assert.assertEquals(0, cityService.getAll().size());
        Assert.assertEquals(0, ipAddressRangeService.getAll().size());

        ipAddressesImportService.importAllIpAddresses(resourceLoader.getResource("classpath:test-data/ip2location-test-ok.csv.gz"));

        List<CountryEntity> countryEntities = countryService.getAll();
        List<RegionEntity> regionEntities = regionService.getAll();
        List<CityEntity> cityEntities = cityService.getAll();
        List<IpAddressRangeEntity> ipAddressRangeEntities = ipAddressRangeService.getAll();

        Assert.assertEquals(OK_IMPORT_NUMBER_OF_COUNTRIES, countryEntities.size());
        Assert.assertEquals(OK_IMPORT_NUMBER_OF_REGIONS, regionService.getAll().size());
        Assert.assertEquals(OK_IMPORT_NUMBER_OF_CITIES, cityService.getAll().size());
        Assert.assertEquals(OK_IMPORT_NUMBER_OF_ROWS, ipAddressRangeService.getAll().size());

        // test contains data
        boolean countryOk = false;
        boolean regionOk = false;
        boolean cityOk = false;
        boolean ipAddressRangeOk = false;

        for (CountryEntity countryEntity : countryEntities) {
            if (INCLUDED_COUNTRY_CODE.equals(countryEntity.getCode())) {
                countryOk = true;
                break;
            }
        }

        for (RegionEntity regionEntity : regionEntities) {
            if (INCLUDED_REGION_NAME.equals(regionEntity.getName())) {
                regionOk = true;
                break;
            }
        }

        for (CityEntity cityEntity : cityEntities) {
            if (INCLUDED_CITY_NAME.equals(cityEntity.getName())) {
                cityOk = true;
                break;
            }
        }

        for (IpAddressRangeEntity ipAddressRangeEntity : ipAddressRangeEntities) {
            if (INCLUDED_IP_RANGE_FROM.equals(ipAddressRangeEntity.getRangeFrom())) {
                ipAddressRangeOk = true;
                break;
            }
        }

        Assert.assertTrue("country missing", countryOk);
        Assert.assertTrue("region missing", regionOk);
        Assert.assertTrue("city missing", cityOk);
        Assert.assertTrue("ip address range from missing", ipAddressRangeOk);
    }

    @Test(expected = ImportRowFailedException.class)
    public void testImportInvalidCsv() throws Exception {
        ipAddressesImportService.importAllIpAddresses(resourceLoader.getResource("classpath:test-data/ip2location-test-corrupted.csv.gz"));
    }

}
