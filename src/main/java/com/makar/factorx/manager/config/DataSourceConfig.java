package com.makar.factorx.manager.config;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@RequiredArgsConstructor
public class DataSourceConfig {

    @Bean
    @Primary
    DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    DataSource dataSource() {
        return dataSourceProperties()
            .initializeDataSourceBuilder()
            .build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.manager")
    DataSourceProperties managerDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    DataSource managerDataSource() {
        return managerDataSourceProperties()
            .initializeDataSourceBuilder()
            .build();
    }
}
