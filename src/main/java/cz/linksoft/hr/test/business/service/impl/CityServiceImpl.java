package cz.linksoft.hr.test.business.service.impl;

import cz.linksoft.hr.test.business.dao.CityDao;
import cz.linksoft.hr.test.business.entity.CityEntity;
import cz.linksoft.hr.test.business.entity.IpAddressRangeEntity;
import cz.linksoft.hr.test.business.service.CityService;
import cz.linksoft.hr.test.business.service.IpAddressRangeService;
import cz.linksoft.hr.test.business.service.generic.AbstractEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of CityService.
 *
 * @author jakubchalupa
 * @since 24.10.17
 */
@Service
public class CityServiceImpl extends AbstractEntityService<CityEntity, CityDao> implements CityService {

    protected IpAddressRangeService ipAddressRangeService;

    @Autowired
    public CityServiceImpl(CityDao dao, IpAddressRangeService ipAddressRangeService) {
        super(dao);
        this.ipAddressRangeService = ipAddressRangeService;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<CityEntity> findByRegionId(Long regionId) {
        return regionId != null ? dao.findByRegionId(regionId) : new ArrayList<>();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<CityEntity> findByCountryId(Long countryId) {
        return countryId != null ? dao.findByCountryId(countryId) : new ArrayList<>();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public CityEntity guessCityForIpNumber(Long ipNumber) {
        if (ipNumber == null) {
            return null;
        }

        IpAddressRangeEntity ipAddressRangeEntity = ipAddressRangeService.findByIpNumber(ipNumber);
        if (ipAddressRangeEntity == null) {
            return null;
        }

        CityEntity cityEntity = dao.find(ipAddressRangeEntity.getCity().getId());
        if (cityEntity != null) {
            // needs to return lazy loaded region
            // noinspection ResultOfMethodCallIgnored
            cityEntity.getRegion().getId();
        }

        return cityEntity;
    }
}
