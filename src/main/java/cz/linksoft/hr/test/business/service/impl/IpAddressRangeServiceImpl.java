package cz.linksoft.hr.test.business.service.impl;

import cz.linksoft.hr.test.business.dao.IpAddressRangeDao;
import cz.linksoft.hr.test.business.entity.IpAddressRangeEntity;
import cz.linksoft.hr.test.business.service.IpAddressRangeService;
import cz.linksoft.hr.test.business.service.generic.AbstractEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of IpAddressRangeService.
 *
 * @author jakubchalupa
 * @since 24.10.17
 */
@Service
public class IpAddressRangeServiceImpl extends AbstractEntityService<IpAddressRangeEntity, IpAddressRangeDao> implements IpAddressRangeService {

    @Autowired
    public IpAddressRangeServiceImpl(IpAddressRangeDao dao) {
        super(dao);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<IpAddressRangeEntity> findByCityId(Long cityId) {
        return cityId != null ? dao.findByCityId(cityId) : new ArrayList<>();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public IpAddressRangeEntity findByIpNumber(Long ipNumber) {
        return dao.findByIpNumber(ipNumber);
    }
}
