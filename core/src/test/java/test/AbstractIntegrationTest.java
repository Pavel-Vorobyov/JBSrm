package test;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ContextConfiguration(classes = IntegrationTestConfig.class)
public abstract class AbstractIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests {

    @PersistenceContext
    protected EntityManager entityManager;

    protected void flushAndClear() {
        entityManager.flush();
        entityManager.clear();
    }
}
