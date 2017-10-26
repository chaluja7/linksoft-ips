package cz.linksoft.hr.test.business.service;

import cz.linksoft.hr.test.business.entity.IpAddressRangeEntity;
import cz.linksoft.hr.test.business.service.generic.EntityService;

import java.util.List;

/**
 * Common interface for all IpAddressRangeService implementations.
 *
 * @author jakubchalupa
 * @since 24.10.17
 */
public interface IpAddressRangeService extends EntityService<IpAddressRangeEntity> {

    /**
     * @param cityId id of city
     * @return all ip address ranges from given city
     */
    List<IpAddressRangeEntity> findByCityId(Long cityId);

    /**
     * @param ipNumber decimal ip Number
     * @return ip address range entity, that given ip number belongs to
     */
    IpAddressRangeEntity findByIpNumber(Long ipNumber);

}
