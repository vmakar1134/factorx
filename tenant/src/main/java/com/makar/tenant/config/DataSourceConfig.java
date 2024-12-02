package com.makar.tenant.config;

import com.makar.tenant.connection.TenantAwareDataSource;
import com.makar.tenant.security.TokenIdentifierResolver;

import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {

    @Bean
    DataSource dataSource(DataSourceProperties dataSourceProperties, TokenIdentifierResolver tokenIdentifierResolver) {
        final DataSource dataSource = dataSourceProperties.initializeDataSourceBuilder().build();
        return new TenantAwareDataSource(dataSource, tokenIdentifierResolver);
    }

}
