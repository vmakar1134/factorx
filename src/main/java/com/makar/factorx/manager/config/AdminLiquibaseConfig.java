
package com.makar.factorx.manager.config;

import javax.sql.DataSource;
import liquibase.integration.spring.SpringLiquibase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AdminLiquibaseConfig {

    // TODO: move to properties class.
    public static final String CLASSPATH_DB_ADMIN_MASTER_XML = "classpath:db/manager/master.xml";

    @Qualifier("managerDataSource")
    private final DataSource adminDataSource;

    @Bean
    SpringLiquibase springLiquibase() {
        var springLiquibase = new SpringLiquibase();
        springLiquibase.setDataSource(adminDataSource);
        springLiquibase.setChangeLog(CLASSPATH_DB_ADMIN_MASTER_XML);
        return springLiquibase;
    }
}
