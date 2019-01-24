package test;

import com.pavel.jbsrm.common.utill.PropertiesUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:test.properties")
@ComponentScan(basePackages = {"com.pavel.jbsrm"})
public class IntegrationTestConfig {

//    @Bean


    @Bean(destroyMethod = "shutdown")
    DataSource dataSource(Environment env) {
        HikariConfig config = new HikariConfig();

        config.setDataSourceProperties(PropertiesUtil.getSubProperties("C:\\Users\\Pavel.Varabyou\\Desktop\\operator-finder-java11\\JBSrm\\core\\src\\test\\resources\\test.properties", "datasource.properties."));
        config.setDataSourceClassName(env.getProperty("datasource.dataSourceClassName"));
        return new HikariDataSource(config);
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
