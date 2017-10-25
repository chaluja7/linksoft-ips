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

    List<IpAddressRangeEntity> findByCityId(Long cityId);

    IpAddressRangeEntity findByIpNumber(Long ipNumber);

}
