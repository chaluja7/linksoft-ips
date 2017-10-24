package cz.linksoft.hr.test;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Dummy entity on 'dual' table just for testing purposes.
 */
@Entity(name = DummyEntity.ENTITY_NAME)
@Table(name = "dual")
class DummyEntity {

	public static final String ENTITY_NAME = "TEST-DummyEntity";

	@Id
	@Column(name = "x")
	private int x;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

}
