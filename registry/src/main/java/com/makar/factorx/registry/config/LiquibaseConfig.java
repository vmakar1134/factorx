
package com.makar.factorx.registry.config;

import javax.sql.DataSource;
import liquibase.integration.spring.SpringLiquibase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class LiquibaseConfig {

    private final DataSource dataSource;

    @Bean
    @ConfigurationProperties("spring.liquibase.registry")
    LiquibaseProperties registryProperties() {
        return new LiquibaseProperties();
    }

    @Bean
    @ConfigurationProperties("spring.liquibase.tenant")
    LiquibaseProperties tenantProperties() {
        return new LiquibaseProperties();
    }

    @Bean
    SpringLiquibase springLiquibase() {
        var springLiquibase = new SpringLiquibase();
        springLiquibase.setDataSource(dataSource);
        springLiquibase.setChangeLog(registryProperties().getChangeLog());
        return springLiquibase;
    }
}
