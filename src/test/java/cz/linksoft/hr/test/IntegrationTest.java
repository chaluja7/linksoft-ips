package cz.linksoft.hr.test;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Annotation to mark integration test with usage of standard Spring web-app context.
 *
 * <p>
 *     Don't forget to declare {@link org.junit.runner.RunWith} annotation with {@link SpringJUnit4ClassRunner} runner
 *     on each test class on which is this annotation used.
 * </p>
 *
 * @see RunWith
 * @see SpringJUnit4ClassRunner
 */
@ContextConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@Rollback
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface IntegrationTest {
}
