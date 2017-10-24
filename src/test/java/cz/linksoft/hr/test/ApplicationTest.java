package cz.linksoft.hr.test;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Basic test to check whether {@link Application} is usable as expected.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@IntegrationTest
public class ApplicationTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	public void embeddedDatabaseIsOk() {
		Integer result = jdbcTemplate.queryForObject("select 1 from dual", Integer.class);
		assertEquals(Integer.valueOf(1), result);
	}

	@Test
	public void jpaAndHibernateIsOk() {
		DummyEntity dummyEntity1 = entityManager.find(DummyEntity.class, 1);
		assertNotNull("Nothing found in 'dual' table with x=1", dummyEntity1);
		assertEquals(1, dummyEntity1.getX());

		try {
			assertNull("x = 0", entityManager.find(DummyEntity.class, 0));
			assertNull("x = 2", entityManager.find(DummyEntity.class, 2));
		} catch (AssertionError e) {
			throw new AssertionError("Nothing should be found in 'dual' table with x != 1", e);
		}
	}

}
