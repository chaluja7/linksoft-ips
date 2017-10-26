package cz.linksoft.hr.test.business.dao;

import cz.linksoft.hr.test.IntegrationTest;
import cz.linksoft.hr.test.business.entity.CityEntity;
import cz.linksoft.hr.test.business.entity.CountryEntity;
import cz.linksoft.hr.test.business.entity.IpAddressRangeEntity;
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
 * Integration test with DB for ipAddressRange DAO.
 *
 * @author cen83414
 * @since 26.10.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@IntegrationTest
public class IpAddressRangeDaoIT {

    @Autowired
    private CountryDao countryDao;

    @Autowired
    private RegionDao regionDao;

    @Autowired
    private CityDao cityDao;

    @Autowired
    private IpAddressRangeDao ipAddressRangeDao;

    private CountryEntity countryEntity;
    private RegionEntity regionEntity;
    private CityEntity cityEntity;

    @Before
    public void init() {
        countryEntity = CountryDaoIT.getCountryEntity("XX", "country");
        countryDao.create(countryEntity);

        regionEntity = RegionDaoIT.getRegionEntity("region", countryEntity);
        regionDao.create(regionEntity);

        cityEntity = CityDaoIT.getCityEntity("city", 49.0, 50.0, regionEntity);
        cityDao.create(cityEntity);
    }

    @Test
    public void testCreateRead() {
        final Long rangeFrom = 50L;
        final Long rangeTo = 60L;

        IpAddressRangeEntity ipAddressRangeEntity = getIpAddressRangeEntity(rangeFrom, rangeTo, cityEntity);

        // persist and check
        ipAddressRangeDao.create(ipAddressRangeEntity);
        Assert.assertNotNull(ipAddressRangeEntity.getId());

        // retrieve and check
        IpAddressRangeEntity retrieved = ipAddressRangeDao.find(ipAddressRangeEntity.getId());
        Assert.assertNotNull(retrieved);
        Assert.assertEquals(rangeFrom, retrieved.getRangeFrom());
        Assert.assertEquals(rangeTo, retrieved.getRangeTo());

        Assert.assertNotNull(retrieved.getCity());
        Assert.assertEquals(cityEntity.getId(), retrieved.getCity().getId());

        Assert.assertNotNull(retrieved.getCity().getRegion());
        Assert.assertEquals(regionEntity.getId(), retrieved.getCity().getRegion().getId());

        Assert.assertNotNull(retrieved.getCity().getRegion().getCountry());
        Assert.assertEquals(countryEntity.getId(), retrieved.getCity().getRegion().getCountry().getId());
    }

    @Test
    public void testFindByCityId() {
        // check no ipAddresses exists for city
        List<IpAddressRangeEntity> retrieved = ipAddressRangeDao.findByCityId(cityEntity.getId());
        Assert.assertNotNull(retrieved);
        Assert.assertTrue(retrieved.isEmpty());

        IpAddressRangeEntity ipAddressRangeEntity = getIpAddressRangeEntity(50L, 60L, cityEntity);

        // persist and check
        ipAddressRangeDao.create(ipAddressRangeEntity);
        Assert.assertNotNull(ipAddressRangeEntity.getId());

        // retrieve by city id and check
        retrieved = ipAddressRangeDao.findByCityId(cityEntity.getId());
        Assert.assertNotNull(retrieved);
        Assert.assertEquals(1, retrieved.size());
        Assert.assertEquals(ipAddressRangeEntity.getRangeFrom(), retrieved.get(0).getRangeFrom());
        Assert.assertEquals(cityEntity.getId(), retrieved.get(0).getCity().getId());
    }

    @Test
    public void testFindByIpNumber() {
        final Long ipNumber = 50L;

        // check no ipAddress exists for ipNumber
        IpAddressRangeEntity retrieved = ipAddressRangeDao.findByIpNumber(ipNumber);
        Assert.assertNull(retrieved);

        final Long entityRangeIpFrom = ipNumber - 10;
        final Long entityRangeIpTo = ipNumber + 10;
        IpAddressRangeEntity ipAddressRangeEntity = getIpAddressRangeEntity(entityRangeIpFrom, entityRangeIpTo, cityEntity);

        // persist and check
        ipAddressRangeDao.create(ipAddressRangeEntity);
        Assert.assertNotNull(ipAddressRangeEntity.getId());

        // retrieve by valid ipNumber id and check
        retrieved = ipAddressRangeDao.findByIpNumber(ipNumber);
        Assert.assertNotNull(retrieved);
        Assert.assertEquals(ipAddressRangeEntity.getId(), retrieved.getId());

        retrieved = ipAddressRangeDao.findByIpNumber(entityRangeIpFrom);
        Assert.assertNotNull(retrieved);
        Assert.assertEquals(ipAddressRangeEntity.getId(), retrieved.getId());

        retrieved = ipAddressRangeDao.findByIpNumber(entityRangeIpTo);
        Assert.assertNotNull(retrieved);
        Assert.assertEquals(ipAddressRangeEntity.getId(), retrieved.getId());

        // retrieve by ipNumber that does not match
        retrieved = ipAddressRangeDao.findByIpNumber(entityRangeIpFrom - 1);
        Assert.assertNull(retrieved);

        retrieved = ipAddressRangeDao.findByIpNumber(entityRangeIpTo + 1);
        Assert.assertNull(retrieved);
    }

    @Test(expected = ConstraintViolationException.class)
    public void testCreate_negativeRangeFrom() {
        IpAddressRangeEntity ipAddressRangeEntity = getIpAddressRangeEntity(-5L, 10L, cityEntity);
        ipAddressRangeDao.create(ipAddressRangeEntity);
    }

    @Test(expected = ConstraintViolationException.class)
    public void testCreate_negativeRangeTo() {
        IpAddressRangeEntity ipAddressRangeEntity = getIpAddressRangeEntity(-5L, 10L, cityEntity);
        ipAddressRangeDao.create(ipAddressRangeEntity);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testCreate_nullCity() {
        ipAddressRangeDao.create(getIpAddressRangeEntity(10L, 20L, null));
    }

    public static IpAddressRangeEntity getIpAddressRangeEntity(Long rangeFrom, Long rangeTo, CityEntity city) {
        IpAddressRangeEntity ipAddressRangeEntity = new IpAddressRangeEntity();
        ipAddressRangeEntity.setRangeFrom(rangeFrom);
        ipAddressRangeEntity.setRangeTo(rangeTo);
        ipAddressRangeEntity.setCity(city);

        return ipAddressRangeEntity;
    }

}
