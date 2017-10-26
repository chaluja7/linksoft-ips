package cz.linksoft.hr.test.business.dao;

import cz.linksoft.hr.test.business.dao.generic.AbstractGenericJpaDao;
import cz.linksoft.hr.test.business.entity.IpAddressRangeEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * IP address range DAO.
 *
 * @author jakubchalupa
 * @since 24.10.17
 */
@Repository
public class IpAddressRangeDao extends AbstractGenericJpaDao<IpAddressRangeEntity> {

    public IpAddressRangeDao() {
        super(IpAddressRangeEntity.class);
    }

    /**
     * @param cityId id of city
     * @return all ip address ranges from given city
     */
    public List<IpAddressRangeEntity> findByCityId(Long cityId) {
        return em.createNamedQuery("IpAddressRangeEntity.findByCityId", IpAddressRangeEntity.class).setParameter("cityId", cityId).getResultList();
    }

    /**
     * @param ipNumber decimal ip Number
     * @return ip address range entity, that given ip number belongs to
     */
    public IpAddressRangeEntity findByIpNumber(Long ipNumber) {
        final List<IpAddressRangeEntity> ipNumbers = em.createQuery(
            "select i from IpAddressRangeEntity i where :ipNumber between i.rangeFrom and i.rangeTo", IpAddressRangeEntity.class)
            .setParameter("ipNumber", ipNumber).getResultList();

        // return first result if more exist
        return ipNumbers.isEmpty() ? null : ipNumbers.get(0);
    }

}
