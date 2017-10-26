package cz.linksoft.hr.test.business.dao;

import cz.linksoft.hr.test.IntegrationTest;
import cz.linksoft.hr.test.business.entity.CountryEntity;
import cz.linksoft.hr.test.business.entity.RegionEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author cen83414
 * @since 26.10.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@IntegrationTest
public class RegionDaoIT {

    @Autowired
    private CountryDao countryDao;

    @Autowired
    private RegionDao regionDao;

    private CountryEntity countryEntity;

    @Before
    public void init() {
        countryEntity = CountryDaoIT.getCountryEntity("XX", "country");
        countryDao.create(countryEntity);
    }

    @Test
    public void testCreateRead() {
        final String regionName = "regionName";

        RegionEntity regionEntity = getRegionEntity(regionName, countryEntity);

        // persist and check
        regionDao.create(regionEntity);
        Assert.assertNotNull(regionEntity.getId());

        // retrieve and check
        RegionEntity retrieved = regionDao.find(regionEntity.getId());
        Assert.assertNotNull(retrieved);
        Assert.assertEquals(regionName, retrieved.getName());

        Assert.assertNotNull(retrieved.getCountry());
        Assert.assertEquals(countryEntity.getId(), retrieved.getCountry().getId());
    }

    @Test
    public void testFindByCountryId() {
        // check no regions exists for country
        List<RegionEntity> retrieved = regionDao.findByCountryId(countryEntity.getId());
        Assert.assertNotNull(retrieved);
        Assert.assertTrue(retrieved.isEmpty());

        final String regionName = "regionName";
        RegionEntity regionEntity = getRegionEntity(regionName, countryEntity);

        // persist and check
        regionDao.create(regionEntity);
        Assert.assertNotNull(regionEntity.getId());

        // retrieve by country id and check
        retrieved = regionDao.findByCountryId(countryEntity.getId());
        Assert.assertNotNull(retrieved);
        Assert.assertEquals(1, retrieved.size());
        Assert.assertEquals(regionName, retrieved.get(0).getName());
        Assert.assertEquals(countryEntity.getId(), retrieved.get(0).getCountry().getId());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testCreate_nullCountry() {
        regionDao.create(getRegionEntity("name", null));
    }

    public static RegionEntity getRegionEntity(String name, CountryEntity country) {
        RegionEntity regionEntity = new RegionEntity();
        regionEntity.setName(name);
        regionEntity.setCountry(country);

        return regionEntity;
    }

}
