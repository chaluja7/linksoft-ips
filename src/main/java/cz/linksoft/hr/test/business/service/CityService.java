package cz.linksoft.hr.test.business.service;

import cz.linksoft.hr.test.business.entity.CityEntity;
import cz.linksoft.hr.test.business.service.generic.EntityService;

import java.util.List;

/**
 * Common interface for all PersonService implementations.
 *
 * @author jakubchalupa
 * @since 24.10.17
 */
public interface CityService extends EntityService<CityEntity> {

    List<CityEntity> findByRegionId(Long regionId);

    List<CityEntity> findByCountryId(Long countryId);

    CityEntity guessCityForIpNumber(Long ipNumber);

}
