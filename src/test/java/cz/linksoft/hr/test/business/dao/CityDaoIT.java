package cz.linksoft.hr.test.business.dao;

import cz.linksoft.hr.test.IntegrationTest;
import cz.linksoft.hr.test.business.entity.CityEntity;
import cz.linksoft.hr.test.business.entity.CountryEntity;
import cz.linksoft.hr.test.business.entity.RegionEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * Integration test with DB for city DAO.
 *
 * @author cen83414
 * @since 26.10.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@IntegrationTest
public class CityDaoIT {

    @Autowired
    private CountryDao countryDao;

    @Autowired
    private RegionDao regionDao;

    @Autowired
    private CityDao cityDao;

    private CountryEntity countryEntity;
    private RegionEntity regionEntity;

    @Before
    public void init() {
        countryEntity = CountryDaoIT.getCountryEntity("XX", "country");
        countryDao.create(countryEntity);

        regionEntity = RegionDaoIT.getRegionEntity("region", countryEntity);
        regionDao.create(regionEntity);
    }

    @Test
    public void testCreateRead() {
        final String cityName = "cityName";
        final Double lat = 50.0;
        final Double lon = 60.0;

        CityEntity cityEntity = getCityEntity(cityName, lat, lon, regionEntity);

        // persist and check
        cityDao.create(cityEntity);
        Assert.assertNotNull(cityEntity.getId());

        // retrieve and check
        CityEntity retrieved = cityDao.find(cityEntity.getId());
        Assert.assertNotNull(retrieved);
        Assert.assertEquals(cityName, retrieved.getName());
        Assert.assertEquals(lat, retrieved.getLatitude());
        Assert.assertEquals(lon, retrieved.getLongitude());

        Assert.assertNotNull(retrieved.getRegion());
        Assert.assertEquals(regionEntity.getId(), retrieved.getRegion().getId());

        Assert.assertNotNull(retrieved.getRegion().getCountry());
        Assert.assertEquals(countryEntity.getId(), retrieved.getRegion().getCountry().getId());
    }

    @Test
    public void testFindByRegionId() {
        // check no cities exists for region
        List<CityEntity> retrieved = cityDao.findByRegionId(regionEntity.getId());
        Assert.assertNotNull(retrieved);
        Assert.assertTrue(retrieved.isEmpty());

        final String cityName = "cityName";
        CityEntity cityEntity = getCityEntity(cityName, 50.0, 60.0, regionEntity);

        // persist and check
        cityDao.create(cityEntity);
        Assert.assertNotNull(cityEntity.getId());

        // retrieve by region id and check
        retrieved = cityDao.findByRegionId(regionEntity.getId());
        Assert.assertNotNull(retrieved);
        Assert.assertEquals(1, retrieved.size());
        Assert.assertEquals(cityName, retrieved.get(0).getName());
        Assert.assertEquals(regionEntity.getId(), retrieved.get(0).getRegion().getId());
    }

    @Test
    public void testFindByCountryId() {
        // check no cities exists for country
        List<CityEntity> retrieved = cityDao.findByCountryId(countryEntity.getId());
        Assert.assertNotNull(retrieved);
        Assert.assertTrue(retrieved.isEmpty());

        final String cityName = "cityName";
        CityEntity cityEntity = getCityEntity(cityName, 50.0, 60.0, regionEntity);

        // persist and check
        cityDao.create(cityEntity);
        Assert.assertNotNull(cityEntity.getId());

        // retrieve by country id and check
        retrieved = cityDao.findByCountryId(countryEntity.getId());
        Assert.assertNotNull(retrieved);
        Assert.assertEquals(1, retrieved.size());
        Assert.assertEquals(cityName, retrieved.get(0).getName());
        Assert.assertEquals(countryEntity.getId(), retrieved.get(0).getRegion().getCountry().getId());
    }

    @Test(expected = ConstraintViolationException.class)
    public void testCreate_latTooLarge() {
        // country code too long
        CityEntity cityEntity = getCityEntity("name", 210.0, 50.0, regionEntity);
        cityDao.create(cityEntity);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testCreate_nullRegion() {
        cityDao.create(getCityEntity("name", 50.0, 60.0, null));
    }

    public static CityEntity getCityEntity(String name, Double lat, Double lon, RegionEntity region) {
        CityEntity cityEntity = new CityEntity();
        cityEntity.setName(name);
        cityEntity.setLatitude(lat);
        cityEntity.setLongitude(lon);
        cityEntity.setRegion(region);

        return cityEntity;
    }

}
