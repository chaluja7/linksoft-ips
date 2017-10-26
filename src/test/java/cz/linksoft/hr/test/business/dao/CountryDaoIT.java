package cz.linksoft.hr.test.business.dao;

import cz.linksoft.hr.test.IntegrationTest;
import cz.linksoft.hr.test.business.entity.CountryEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.ConstraintViolationException;

/**
 * @author cen83414
 * @since 26.10.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@IntegrationTest
public class CountryDaoIT {

    @Autowired
    private CountryDao countryDao;

    @Test
    public void testCreateRead() {
        final String code = "xx";
        final String name = "name";

        CountryEntity countryEntity = getCountryEntity(code, name);

        // persist and check
        countryDao.create(countryEntity);
        Assert.assertNotNull(countryEntity.getId());

        // retrieve and check
        CountryEntity retrieved = countryDao.find(countryEntity.getId());
        Assert.assertNotNull(retrieved);
        Assert.assertEquals(code, retrieved.getCode());
        Assert.assertEquals(name, retrieved.getName());
    }

    @Test(expected = ConstraintViolationException.class)
    public void testCreate_codeTooLong() {
        // country code too long
        CountryEntity countryEntity = getCountryEntity("xxxx", "aa");
        countryDao.create(countryEntity);
    }

    public static CountryEntity getCountryEntity(String code, String name) {
        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setCode(code);
        countryEntity.setName(name);

        return countryEntity;
    }

}
