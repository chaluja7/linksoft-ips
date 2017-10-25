package cz.linksoft.hr.test.business.service.generic;

import cz.linksoft.hr.test.business.dao.generic.GenericDao;
import cz.linksoft.hr.test.business.entity.AbstractEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Abstract service class wrapping implementation of basic CRUD operations over DB entities.
 *
 * @author jakubchalupa
 * @since 24.10.17
 */
public abstract class AbstractEntityService<ENT extends AbstractEntity, DAO extends GenericDao<ENT>> implements EntityService<ENT> {

    /**
     * concrete dao implementation. Descendant must inject this field in its constructor.
     */
    protected DAO dao;

    /**
     * @param dao concrete dao implementation from descendant
     */
    public AbstractEntityService(DAO dao) {
        this.dao = dao;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public ENT get(Long id) {
        return id != null ? dao.find(id) : null;
    }

    @Override
    @Transactional
    public void update(ENT entity) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    @Transactional
    public void create(ENT entity) {
        dao.create(entity);
    }

    @Override
    @Transactional
    public void delete(long id) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<ENT> getAll() {
        return dao.getAll();
    }

}
