package cz.linksoft.hr.test.business.dao;

import cz.linksoft.hr.test.business.dao.generic.AbstractGenericJpaDao;
import cz.linksoft.hr.test.business.entity.RegionEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Region DAO.
 *
 * @author jakubchalupa
 * @since 24.10.17
 */
@Repository
public class RegionDao extends AbstractGenericJpaDao<RegionEntity> {

    public RegionDao() {
        super(RegionEntity.class);
    }

    public List<RegionEntity> findByCountryId(long countryId) {
        return em.createNamedQuery("RegionEntity.findByCountryId", RegionEntity.class).setParameter("countryId", countryId).getResultList();
    }

}
