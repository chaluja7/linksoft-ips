package cz.linksoft.hr.test.business.service.impl;

import cz.linksoft.hr.test.business.dao.RegionDao;
import cz.linksoft.hr.test.business.entity.RegionEntity;
import cz.linksoft.hr.test.business.service.RegionService;
import cz.linksoft.hr.test.business.service.generic.AbstractEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of RegionService.
 *
 * @author jakubchalupa
 * @since 24.10.17
 */
@Service
public class RegionServiceImpl extends AbstractEntityService<RegionEntity, RegionDao> implements RegionService {

    @Autowired
    public RegionServiceImpl(RegionDao dao) {
        super(dao);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<RegionEntity> findByCountryId(Long countryId) {
        return countryId != null ? dao.findByCountryId(countryId) : new ArrayList<>();
    }
}
