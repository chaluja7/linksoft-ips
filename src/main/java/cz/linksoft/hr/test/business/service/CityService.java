package cz.linksoft.hr.test.business.service;

import cz.linksoft.hr.test.business.entity.CityEntity;
import cz.linksoft.hr.test.business.service.generic.EntityService;

import java.util.List;

/**
 * Common interface for all CityService implementations.
 *
 * @author jakubchalupa
 * @since 24.10.17
 */
public interface CityService extends EntityService<CityEntity> {

    /**
     * @param regionId id of region
     * @return all cities that belong to given region
     */
    List<CityEntity> findByRegionId(Long regionId);

    /**
     * @param countryId id of country
     * @return all cities that belong to given country
     */
    List<CityEntity> findByCountryId(Long countryId);

    /**
     * @param ipNumber decimal IP number
     * @return city that most probably owns given ip number, null if this city does not exists
     */
    CityEntity guessCityForIpNumber(Long ipNumber);

}
