package cz.linksoft.hr.test.business.service.impl;

import cz.linksoft.hr.test.business.dao.RegionDao;
import cz.linksoft.hr.test.business.entity.RegionEntity;
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
public class RegionServiceImplTest extends EasyMockSupport {

    private RegionServiceImpl regionService;
    private RegionDao regionDaoMock = createMock(RegionDao.class);

    @Before
    public void init() {
        regionService = new RegionServiceImpl(regionDaoMock);
    }

    @Test
    public void testFindByIdNonNull() {
        final Long id = 5L;

        final RegionEntity regionEntity = new RegionEntity();
        regionEntity.setId(id);
        regionEntity.setName("foo");

        EasyMock.expect(regionDaoMock.find(id)).andReturn(regionEntity);

        replayAll();

        final RegionEntity retrieved = regionService.find(id);
        Assert.assertNotNull(retrieved);
        Assert.assertEquals(id, retrieved.getId());
        Assert.assertEquals(regionEntity.getName(), retrieved.getName());
        verifyAll();
    }

    @Test
    public void testFindByIdNull() throws Exception {
        final Long id = null;

        replayAll();

        final RegionEntity retrieved = regionService.find(id);
        Assert.assertNull(retrieved);
        verifyAll();
    }

    @Test
    public void testFindAll() throws Exception {
        final RegionEntity regionEntity = new RegionEntity();
        regionEntity.setId(1L);
        regionEntity.setName("foo");

        EasyMock.expect(regionDaoMock.getAll()).andReturn(Collections.singletonList(regionEntity));

        replayAll();

        final List<RegionEntity> retrieved = regionService.getAll();
        Assert.assertNotNull(retrieved);
        Assert.assertEquals(1, retrieved.size());
        Assert.assertEquals(regionEntity.getId(), retrieved.get(0).getId());
        Assert.assertEquals(regionEntity.getName(), retrieved.get(0).getName());
        verifyAll();
    }

    @Test
    public void testFindByCountryIdNonNull() {
        final Long regionId = 1L;
        final Long countryId = 5L;

        final RegionEntity regionEntity = new RegionEntity();
        regionEntity.setId(regionId);
        regionEntity.setName("foo");

        EasyMock.expect(regionDaoMock.findByCountryId(countryId)).andReturn(Collections.singletonList(regionEntity));

        replayAll();

        final List<RegionEntity> retrieved = regionService.findByCountryId(countryId);
        Assert.assertNotNull(retrieved);
        Assert.assertFalse(retrieved.isEmpty());
        Assert.assertEquals(regionId, retrieved.get(0).getId());
        Assert.assertEquals(regionEntity.getName(), retrieved.get(0).getName());
        verifyAll();
    }

    @Test
    public void testFindByCountryIdNull() throws Exception {
        final Long countryId = null;

        replayAll();

        final List<RegionEntity> retrieved = regionService.findByCountryId(countryId);
        Assert.assertNotNull(retrieved);
        Assert.assertTrue(retrieved.isEmpty());
        verifyAll();
    }

}
