package test;

import com.pavel.jbsrm.common.config.LiquibaseConfig;
import com.pavel.jbsrm.common.config.PersistenceConfig;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ContextConfiguration(classes = {IntegrationTestConfig.class, PersistenceConfig.class, LiquibaseConfig.class})
@ActiveProfiles(profiles = "test")
public abstract class AbstractIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests {

    @PersistenceContext
    protected EntityManager entityManager;

    protected void flushAndClear() {
        entityManager.flush();
        entityManager.clear();
    }
}
