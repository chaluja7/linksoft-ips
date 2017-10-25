package cz.linksoft.hr.test.business.dao.generic;


import cz.linksoft.hr.test.business.entity.AbstractEntity;

import java.util.List;

/**
 * Common interface for all Dao implementation.
 *
 * @author jakubchalupa
 * @since 24.10.17
 */
public interface GenericDao<ENT extends AbstractEntity> {

    /**
     * persist entity
     * @param entity entity to persist
     */
    void create(ENT entity);

    /**
     * update entity
     * @param entity entity to update
     */
    void update(ENT entity);

    /**
     * find entity by id
     * @param id entity id
     * @return founded entity
     */
    ENT find(long id);

    /**
     * delete entity
     * @param id entity id (to delete)
     */
    void delete(long id);

    /**
     * will find all entities of a type
     * @return list of all entities by type
     */
    List<ENT> getAll();

}
