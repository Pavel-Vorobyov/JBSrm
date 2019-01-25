package com.pavel.jbsrm.common.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

/**
 * Configuration for Liquibase. Parent application should import it explicitly to start applying DB changes
 */
@Configuration
@PropertySource(value = "classpath:liquibase.properties", ignoreResourceNotFound = true)
public class LiquibaseConfig {

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource, Environment environment) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:/db/changelog/liquibase-changelog.xml");
        liquibase.setContexts(String.join(",", environment.getActiveProfiles()));
        return liquibase;
    }
}
