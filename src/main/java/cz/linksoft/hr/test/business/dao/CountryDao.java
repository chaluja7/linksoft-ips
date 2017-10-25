package cz.linksoft.hr.test.business.dao;

import cz.linksoft.hr.test.business.dao.generic.AbstractGenericJpaDao;
import cz.linksoft.hr.test.business.entity.CountryEntity;
import org.springframework.stereotype.Repository;

/**
 * Country DAO.
 *
 * @author jakubchalupa
 * @since 24.10.17
 */
@Repository
public class CountryDao extends AbstractGenericJpaDao<CountryEntity> {

    public CountryDao() {
        super(CountryEntity.class);
    }

}
