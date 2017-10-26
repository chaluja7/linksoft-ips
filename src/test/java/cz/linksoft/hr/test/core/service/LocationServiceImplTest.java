package cz.linksoft.hr.test.core.service;

import cz.linksoft.hr.test.api.model.City;
import cz.linksoft.hr.test.api.model.Country;
import cz.linksoft.hr.test.api.model.Region;
import cz.linksoft.hr.test.business.entity.CityEntity;
import cz.linksoft.hr.test.business.entity.CountryEntity;
import cz.linksoft.hr.test.business.entity.RegionEntity;
import cz.linksoft.hr.test.business.service.CityService;
import cz.linksoft.hr.test.business.service.CountryService;
import cz.linksoft.hr.test.business.service.RegionService;
import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

/**
 * Besides check valid transformation of some entity objects to api object.
 *
 * @author jakubchalupa
 * @since 26.10.17
 */
public class LocationServiceImplTest extends EasyMockSupport {

    private LocationServiceImpl locationService;
    private CountryService countryServiceMock = createMock(CountryService.class);
    private RegionService regionServiceMock = createMock(RegionService.class);
    private CityService cityServiceMock = createMock(CityService.class);

    @Before
    public void init() {
        locationService = new LocationServiceImpl(countryServiceMock, regionServiceMock, cityServiceMock);
    }

    @Test
    public void testGetAllCountries() throws Exception {
        final Long countryId = 5L;
        final String countryCode = "cc";
        final String countryName = "name";

        final CountryEntity countryEntity = new CountryEntity();
        countryEntity.setId(countryId);
        countryEntity.setCode(countryCode);
        countryEntity.setName(countryName);

        EasyMock.expect(countryServiceMock.getAll()).andReturn(Collections.singletonList(countryEntity));

        replayAll();

        List<Country> allCountries = locationService.getAllCountries();
        Assert.assertNotNull(allCountries);
        Assert.assertEquals(1, allCountries.size());

        // check valid transformation
        Country country = allCountries.get(0);
        Assert.assertEquals(countryId, country.getId());
        Assert.assertEquals(countryCode, country.getCode());
        Assert.assertEquals(countryName, country.getName());

        verifyAll();
    }

    @Test
    public void testGetAllCountryRegions() throws Exception {
        final Long countryId = 5L;

        final Long regionId = 10L;
        final String regionName = "name";

        final CountryEntity countryEntity = new CountryEntity();
        countryEntity.setId(countryId);

        final RegionEntity regionEntity = new RegionEntity();
        regionEntity.setId(regionId);
        regionEntity.setName(regionName);
        regionEntity.setCountry(countryEntity);

        EasyMock.expect(regionServiceMock.findByCountryId(countryId)).andReturn(Collections.singletonList(regionEntity));

        replayAll();

        List<Region> allCountryRegions = locationService.getAllCountryRegions(countryId);
        Assert.assertNotNull(allCountryRegions);
        Assert.assertEquals(1, allCountryRegions.size());

        // check valid transformation
        Region region = allCountryRegions.get(0);
        Assert.assertEquals(regionId, region.getId());
        Assert.assertEquals(regionName, region.getName());
        Assert.assertEquals(countryId, region.getCountryId());

        verifyAll();
    }

    @Test
    public void testGetAllRegionCities() throws Exception {
        final Long regionId = 10L;

        final Long cityId = 100L;
        final String cityName = "cityName";
        final Double cityLat = 50.0;
        final Double cityLon = 60.0;

        final RegionEntity regionEntity = new RegionEntity();
        regionEntity.setId(regionId);

        final CityEntity cityEntity = new CityEntity();
        cityEntity.setId(cityId);
        cityEntity.setName(cityName);
        cityEntity.setLatitude(cityLat);
        cityEntity.setLongitude(cityLon);
        cityEntity.setRegion(regionEntity);

        EasyMock.expect(cityServiceMock.findByRegionId(regionId)).andReturn(Collections.singletonList(cityEntity));

        replayAll();

        List<City> allRegionCities = locationService.getAllRegionCities(regionId);
        Assert.assertNotNull(allRegionCities);
        Assert.assertEquals(1, allRegionCities.size());

        // check valid transformation
        City city = allRegionCities.get(0);
        Assert.assertEquals(cityId, city.getId());
        Assert.assertEquals(cityName, city.getName());
        Assert.assertEquals(cityLat, city.getGpsCoordinates().getLatitude());
        Assert.assertEquals(cityLon, city.getGpsCoordinates().getLongtitude());
        Assert.assertEquals(regionId, city.getRegionId());

        verifyAll();
    }

    @Test
    public void testGetAllCountryCitites() throws Exception {
        final Long countryId = 50L;
        final Long regionId = 10L;

        final Long cityId = 100L;
        final String cityName = "cityName";
        final Double cityLat = 50.0;
        final Double cityLon = 60.0;

        final RegionEntity regionEntity = new RegionEntity();
        regionEntity.setId(regionId);

        final CityEntity cityEntity = new CityEntity();
        cityEntity.setId(cityId);
        cityEntity.setName(cityName);
        cityEntity.setLatitude(cityLat);
        cityEntity.setLongitude(cityLon);
        cityEntity.setRegion(regionEntity);

        EasyMock.expect(cityServiceMock.findByCountryId(countryId)).andReturn(Collections.singletonList(cityEntity));

        replayAll();

        List<City> allCountryCitites = locationService.getAllCountryCitites(countryId);
        Assert.assertNotNull(allCountryCitites);
        Assert.assertEquals(1, allCountryCitites.size());

        // check valid transformation
        City city = allCountryCitites.get(0);
        Assert.assertEquals(cityId, city.getId());
        Assert.assertEquals(cityName, city.getName());
        Assert.assertEquals(cityLat, city.getGpsCoordinates().getLatitude());
        Assert.assertEquals(cityLon, city.getGpsCoordinates().getLongtitude());
        Assert.assertEquals(regionId, city.getRegionId());

        verifyAll();
    }


}
