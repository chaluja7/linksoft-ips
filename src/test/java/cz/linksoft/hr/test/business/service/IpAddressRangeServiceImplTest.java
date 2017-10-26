package cz.linksoft.hr.test.business.service;

import cz.linksoft.hr.test.business.dao.IpAddressRangeDao;
import cz.linksoft.hr.test.business.entity.IpAddressRangeEntity;
import cz.linksoft.hr.test.business.service.impl.IpAddressRangeServiceImpl;
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
public class IpAddressRangeServiceImplTest extends EasyMockSupport {

    private IpAddressRangeServiceImpl ipAddressRangeService;
    private IpAddressRangeDao ipAddressRangeDao = createMock(IpAddressRangeDao.class);

    @Before
    public void init() {
        ipAddressRangeService = new IpAddressRangeServiceImpl(ipAddressRangeDao);
    }

    @Test
    public void testFindByIdNonNull() {
        final Long id = 5L;

        final IpAddressRangeEntity ipAddressRangeEntity = new IpAddressRangeEntity();
        ipAddressRangeEntity.setId(id);
        ipAddressRangeEntity.setRangeFrom(10L);

        EasyMock.expect(ipAddressRangeDao.find(id)).andReturn(ipAddressRangeEntity);

        replayAll();

        final IpAddressRangeEntity retrieved = ipAddressRangeService.find(id);
        Assert.assertNotNull(retrieved);
        Assert.assertEquals(id, retrieved.getId());
        Assert.assertEquals(ipAddressRangeEntity.getRangeFrom(), retrieved.getRangeFrom());
        verifyAll();
    }

    @Test
    public void testFindByIdNull() throws Exception {
        final Long id = null;

        replayAll();

        final IpAddressRangeEntity retrieved = ipAddressRangeService.find(id);
        Assert.assertNull(retrieved);
        verifyAll();
    }

    @Test
    public void testFindAll() throws Exception {
        final IpAddressRangeEntity ipAddressRangeEntity = new IpAddressRangeEntity();
        ipAddressRangeEntity.setId(1L);
        ipAddressRangeEntity.setRangeFrom(10L);

        EasyMock.expect(ipAddressRangeDao.getAll()).andReturn(Collections.singletonList(ipAddressRangeEntity));

        replayAll();

        final List<IpAddressRangeEntity> retrieved = ipAddressRangeService.getAll();
        Assert.assertNotNull(retrieved);
        Assert.assertEquals(1, retrieved.size());
        Assert.assertEquals(ipAddressRangeEntity.getId(), retrieved.get(0).getId());
        Assert.assertEquals(ipAddressRangeEntity.getRangeFrom(), retrieved.get(0).getRangeFrom());
        verifyAll();
    }

    @Test
    public void testFindByCityIdNonNull() {
        final Long ipId = 1L;
        final Long cityId = 5L;

        final IpAddressRangeEntity ipAddressRangeEntity = new IpAddressRangeEntity();
        ipAddressRangeEntity.setId(ipId);

        EasyMock.expect(ipAddressRangeDao.findByCityId(cityId)).andReturn(Collections.singletonList(ipAddressRangeEntity));

        replayAll();

        final List<IpAddressRangeEntity> retrieved = ipAddressRangeService.findByCityId(cityId);
        Assert.assertNotNull(retrieved);
        Assert.assertFalse(retrieved.isEmpty());
        Assert.assertEquals(ipId, retrieved.get(0).getId());
        verifyAll();
    }

    @Test
    public void testFindByCityIdNull() throws Exception {
        final Long cityId = null;

        replayAll();

        final List<IpAddressRangeEntity> retrieved = ipAddressRangeService.findByCityId(cityId);
        Assert.assertNotNull(retrieved);
        Assert.assertTrue(retrieved.isEmpty());
        verifyAll();
    }

    @Test
    public void testFindByIpNumber() {
        final Long ipId = 1L;
        final Long ipNumber = 5L;

        final IpAddressRangeEntity ipAddressRangeEntity = new IpAddressRangeEntity();
        ipAddressRangeEntity.setId(ipId);

        EasyMock.expect(ipAddressRangeDao.findByIpNumber(ipNumber)).andReturn(ipAddressRangeEntity);

        replayAll();

        final IpAddressRangeEntity retrieved = ipAddressRangeService.findByIpNumber(ipNumber);
        Assert.assertNotNull(retrieved);
        Assert.assertEquals(ipId, retrieved.getId());
        verifyAll();
    }

}
