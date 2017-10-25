package cz.linksoft.hr.test.business.service.impl;

import cz.linksoft.hr.test.business.dao.CountryDao;
import cz.linksoft.hr.test.business.entity.CountryEntity;
import cz.linksoft.hr.test.business.service.CountryService;
import cz.linksoft.hr.test.business.service.generic.AbstractEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of CountryService.
 *
 * @author jakubchalupa
 * @since 24.10.17
 */
@Service
public class CountryServiceImpl extends AbstractEntityService<CountryEntity, CountryDao> implements CountryService {

    @Autowired
    public CountryServiceImpl(CountryDao dao) {
        super(dao);
    }

}
