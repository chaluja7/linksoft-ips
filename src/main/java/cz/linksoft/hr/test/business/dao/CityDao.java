package cz.linksoft.hr.test.business.dao;

import cz.linksoft.hr.test.business.dao.generic.AbstractGenericJpaDao;
import cz.linksoft.hr.test.business.entity.CityEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * City DAO.
 *
 * @author jakubchalupa
 * @since 24.10.17
 */
@Repository
public class CityDao extends AbstractGenericJpaDao<CityEntity> {

    public CityDao() {
        super(CityEntity.class);
    }

    /**
     * @param regionId id of region
     * @return all cities that belong to given region
     */
    public List<CityEntity> findByRegionId(long regionId) {
        return em.createNamedQuery("CityEntity.findByRegionId", CityEntity.class).setParameter("regionId", regionId).getResultList();
    }

    /**
     * @param countryId id of country
     * @return all cities that belong to given country
     */
    public List<CityEntity> findByCountryId(long countryId) {
        return em.createNamedQuery("CityEntity.findByCountryId", CityEntity.class).setParameter("countryId", countryId).getResultList();
    }

}
