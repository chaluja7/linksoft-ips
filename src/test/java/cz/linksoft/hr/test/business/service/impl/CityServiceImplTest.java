package cz.linksoft.hr.test.business.service.impl;

import cz.linksoft.hr.test.business.dao.CityDao;
import cz.linksoft.hr.test.business.entity.CityEntity;
import cz.linksoft.hr.test.business.entity.IpAddressRangeEntity;
import cz.linksoft.hr.test.business.service.IpAddressRangeService;
import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

/**
 * @author cen83414
 * @since 26.10.2017.
 */
public class CityServiceImplTest extends EasyMockSupport {

    private CityServiceImpl cityService;
    private IpAddressRangeService ipAddressRangeServiceMock = createMock(IpAddressRangeService.class);
    private CityDao cityDaoMock = createMock(CityDao.class);

    @Before
    public void init() {
        cityService = new CityServiceImpl(cityDaoMock, ipAddressRangeServiceMock);
    }

    @Test
    public void testFindByIdNonNull() {
        final Long id = 5L;

        final CityEntity cityEntity = new CityEntity();
        cityEntity.setId(id);
        cityEntity.setName("foo");

        EasyMock.expect(cityDaoMock.find(id)).andReturn(cityEntity);

        replayAll();

        final CityEntity retrieved = cityService.find(id);
        Assert.assertNotNull(retrieved);
        Assert.assertEquals(id, retrieved.getId());
        Assert.assertEquals(cityEntity.getName(), retrieved.getName());
        verifyAll();
    }

    @Test
    public void testFindByIdNull() throws Exception {
        final Long id = null;

        replayAll();

        final CityEntity retrieved = cityService.find(id);
        Assert.assertNull(retrieved);
        verifyAll();
    }

    @Test
    public void testFindAll() throws Exception {
        final CityEntity cityEntity = new CityEntity();
        cityEntity.setId(1L);
        cityEntity.setName("foo");

        EasyMock.expect(cityDaoMock.getAll()).andReturn(Collections.singletonList(cityEntity));

        replayAll();

        final List<CityEntity> retrieved = cityService.getAll();
        Assert.assertNotNull(retrieved);
        Assert.assertEquals(1, retrieved.size());
        Assert.assertEquals(cityEntity.getId(), retrieved.get(0).getId());
        Assert.assertEquals(cityEntity.getName(), retrieved.get(0).getName());
        verifyAll();
    }

    @Test
    public void testFindByRegionIdNonNull() {
        final Long cityId = 1L;
        final Long regionId = 5L;

        final CityEntity cityEntity = new CityEntity();
        cityEntity.setId(cityId);

        EasyMock.expect(cityDaoMock.findByRegionId(regionId)).andReturn(Collections.singletonList(cityEntity));

        replayAll();

        final List<CityEntity> retrieved = cityService.findByRegionId(regionId);
        Assert.assertNotNull(retrieved);
        Assert.assertFalse(retrieved.isEmpty());
        Assert.assertEquals(cityId, retrieved.get(0).getId());
        Assert.assertEquals(cityEntity.getName(), retrieved.get(0).getName());
        verifyAll();
    }

    @Test
    public void testFindByRegionIdNull() throws Exception {
        final Long regionId = null;

        replayAll();

        final List<CityEntity> retrieved = cityService.findByRegionId(regionId);
        Assert.assertNotNull(retrieved);
        Assert.assertTrue(retrieved.isEmpty());
        verifyAll();
    }

    @Test
    public void testFindByCountryIdNonNull() {
        final Long cityId = 1L;
        final Long countryId = 5L;

        final CityEntity cityEntity = new CityEntity();
        cityEntity.setId(cityId);

        EasyMock.expect(cityDaoMock.findByCountryId(countryId)).andReturn(Collections.singletonList(cityEntity));

        replayAll();

        final List<CityEntity> retrieved = cityService.findByCountryId(countryId);
        Assert.assertNotNull(retrieved);
        Assert.assertFalse(retrieved.isEmpty());
        Assert.assertEquals(cityId, retrieved.get(0).getId());
        Assert.assertEquals(cityEntity.getName(), retrieved.get(0).getName());
        verifyAll();
    }

    @Test
    public void testFindByCountryIdNull() throws Exception {
        final Long countryId = null;

        replayAll();

        final List<CityEntity> retrieved = cityService.findByCountryId(countryId);
        Assert.assertNotNull(retrieved);
        Assert.assertTrue(retrieved.isEmpty());
        verifyAll();
    }

    @Test
    public void testGuessCityForIpNumberNoMatch() {
        final Long ipNumber = 1L;

        EasyMock.expect(ipAddressRangeServiceMock.findByIpNumber(ipNumber)).andReturn(null);

        replayAll();

        final CityEntity retrieved = cityService.guessCityForIpNumber(ipNumber);
        Assert.assertNull(retrieved);
        verifyAll();
    }

    @Test
    public void testGuessCityForIpNumberMatch() {
        final Long ipNumber = 1L;
        final Long cityId = 10L;
        final Long ipId = 20L;

        final CityEntity cityEntity = new CityEntity();
        cityEntity.setId(cityId);

        final IpAddressRangeEntity ipAddressRangeEntity = new IpAddressRangeEntity();
        ipAddressRangeEntity.setId(ipId);
        ipAddressRangeEntity.setCity(cityEntity);

        EasyMock.expect(ipAddressRangeServiceMock.findByIpNumber(ipNumber)).andReturn(ipAddressRangeEntity);
        EasyMock.expect(cityDaoMock.find(cityId)).andReturn(cityEntity);

        replayAll();

        final CityEntity retrieved = cityService.guessCityForIpNumber(ipNumber);
        Assert.assertNotNull(retrieved);
        Assert.assertEquals(cityId, retrieved.getId());
        verifyAll();
    }

    @Test
    public void testGuessCityForIpNumberNull() throws Exception {
        final Long ipNumber = null;

        replayAll();

        final CityEntity retrieved = cityService.guessCityForIpNumber(ipNumber);
        Assert.assertNull(retrieved);
        verifyAll();
    }

}
