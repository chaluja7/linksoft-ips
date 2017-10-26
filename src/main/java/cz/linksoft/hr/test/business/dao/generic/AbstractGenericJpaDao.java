package cz.linksoft.hr.test.business.dao.generic;

import cz.linksoft.hr.test.business.entity.AbstractEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Abstract JPA generic dao.
 *
 * @author jakubchalupa
 * @since 24.10.17
 */
public abstract class AbstractGenericJpaDao<ENT extends AbstractEntity> implements GenericDao<ENT> {

    @PersistenceContext
    protected EntityManager em;

    private final Class<ENT> type;

    public AbstractGenericJpaDao(Class<ENT> type) {
        this.type = type;
    }

    @Override
    public void create(ENT entity) {
        em.persist(entity);
    }

    @Override
    public void update(ENT entity) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public ENT find(long id) {
        return em.find(type, id);
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public List<ENT> getAll() {
        return em.createQuery("from " + type.getName(), type).getResultList();
    }
}
