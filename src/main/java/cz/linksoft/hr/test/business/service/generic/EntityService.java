package cz.linksoft.hr.test.business.service.generic;

import cz.linksoft.hr.test.business.entity.AbstractEntity;

import java.util.List;

/**
 * CRUD interface for entities.
 *
 * @author jakubchalupa
 * @since 14.12.16
 */
public interface EntityService<ENT extends AbstractEntity> {

    /**
     * find entity by id
     * @param id id of entity
     * @return entity by id or null
     */
    ENT find(Long id);

    /**
     * update entity
     * @param entity entity to update
     */
    void update(ENT entity);

    /**
     * persist entity
     * @param entity entity to persist
     */
    void create(ENT entity);

    /**
     * delete entity by id
     * @param id id of entity to delete
     */
    void delete(long id);

    /**
     * find all entities of given type
     * @return all entities of given type
     */
    List<ENT> getAll();


}
