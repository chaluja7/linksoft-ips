package cz.linksoft.hr.test.business.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Super class for all entities.
 *
 * @author jakubchalupa
 * @since 24.10.17
 */
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj instanceof AbstractEntity) {
            AbstractEntity other = (AbstractEntity) obj;
            return this.getId() != null && other.getId() != null && this.getId().equals(other.getId());
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return 37 * hash + (this.getId() != null ? this.getId().hashCode() : 0);
    }
}
