package com.makar.tenant.config;

import com.makar.tenant.connection.TenantAwareDataSource;

import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {

    @Bean
    DataSource dataSource(DataSourceProperties dataSourceProperties) {
        final DataSource dataSource = dataSourceProperties.initializeDataSourceBuilder().build();
        return new TenantAwareDataSource(dataSource);
    }

}
