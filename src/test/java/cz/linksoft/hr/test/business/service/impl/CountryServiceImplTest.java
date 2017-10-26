package cz.linksoft.hr.test.business.service.impl;

import cz.linksoft.hr.test.business.dao.CountryDao;
import cz.linksoft.hr.test.business.entity.CountryEntity;
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
public class CountryServiceImplTest extends EasyMockSupport {

    private CountryServiceImpl countryService;
    private CountryDao countryDaoMock = createMock(CountryDao.class);

    @Before
    public void init() {
        countryService = new CountryServiceImpl(countryDaoMock);
    }

    @Test
    public void testFindByIdNonNull() {
        final Long id = 5L;

        final CountryEntity countryEntity = new CountryEntity();
        countryEntity.setId(id);
        countryEntity.setName("foo");

        EasyMock.expect(countryDaoMock.find(id)).andReturn(countryEntity);

        replayAll();

        final CountryEntity retrieved = countryService.find(id);
        Assert.assertNotNull(retrieved);
        Assert.assertEquals(id, retrieved.getId());
        Assert.assertEquals(countryEntity.getName(), retrieved.getName());
        verifyAll();
    }

    @Test
    public void testFindByIdNull() throws Exception {
        final Long id = null;

        replayAll();

        final CountryEntity retrieved = countryService.find(id);
        Assert.assertNull(retrieved);
        verifyAll();
    }

    @Test
    public void testFindAll() throws Exception {
        final CountryEntity countryEntity = new CountryEntity();
        countryEntity.setId(1L);
        countryEntity.setName("foo");

        EasyMock.expect(countryDaoMock.getAll()).andReturn(Collections.singletonList(countryEntity));

        replayAll();

        final List<CountryEntity> retrieved = countryService.getAll();
        Assert.assertNotNull(retrieved);
        Assert.assertEquals(1, retrieved.size());
        Assert.assertEquals(countryEntity.getId(), retrieved.get(0).getId());
        Assert.assertEquals(countryEntity.getName(), retrieved.get(0).getName());
        verifyAll();
    }



}
