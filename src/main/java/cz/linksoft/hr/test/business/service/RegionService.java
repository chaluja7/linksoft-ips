package cz.linksoft.hr.test.business.service;

import cz.linksoft.hr.test.business.entity.RegionEntity;
import cz.linksoft.hr.test.business.service.generic.EntityService;

import java.util.List;

/**
 * Common interface for all RegionService implementations.
 *
 * @author jakubchalupa
 * @since 24.10.17
 */
public interface RegionService extends EntityService<RegionEntity> {

    /**
     * @param countryId if of country
     * @return all regions that belong to given country
     */
    List<RegionEntity> findByCountryId(Long countryId);

}
