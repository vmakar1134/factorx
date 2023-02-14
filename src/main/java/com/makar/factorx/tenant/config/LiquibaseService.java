package com.makar.factorx.tenant.config;

import com.makar.factorx.tenant.domain.MultiSchemaLiquibaseSupport;
import com.makar.factorx.tenant.domain.SingleSchemaLiquibaseSupport;
import java.sql.SQLException;
import java.util.Collection;
import javax.sql.DataSource;
import liquibase.exception.LiquibaseException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Getter
@Setter
@Service
@RequiredArgsConstructor
public class LiquibaseService {

    // TODO: move to properties class.
    private static final String TENANTS_MASTER_XML = "classpath:db/tenants/master.xml";

    private final DataSource dataSource;

    public void runOnSchemas(Collection<String> schemas) {
        log.info("Schema based multitenancy engaged.");

        MultiSchemaLiquibaseSupport
            .baseInstance(dataSource, schemas, TENANTS_MASTER_XML)
            .forEach(this::runUpdate);
    }

    public void runOnSchema(String schema) {
        var liquibaseSupport = SingleSchemaLiquibaseSupport.baseInstance(dataSource, schema, TENANTS_MASTER_XML);
        runUpdate(liquibaseSupport);
    }

    private void runUpdate(SingleSchemaLiquibaseSupport liquibase) {
        var schema = liquibase.getSchema();
        log.info("Initializing Liquibase for schema: {}.", schema);
        createSchemaIfNotExists(schema);
        executeUpdate(liquibase);
        log.info("Liquibase ran for schema: {}. ", schema);
    }

    private void executeUpdate(SingleSchemaLiquibaseSupport liquibase) {
        try {
            liquibase.runUpdate();
        } catch (LiquibaseException e) {
            log.error("Unable to run update on schema: {}.", liquibase.getSchema());
            // TODO: create dedicated exception.
            throw new RuntimeException(e);
        }
    }

    private void createSchemaIfNotExists(String schema) {
        try (
            var connection = dataSource.getConnection();
            var statement = connection.createStatement()
        ) {
            statement.executeUpdate("create schema if not exists " + schema);
        } catch (SQLException e) {
            log.error("Unable to create schema: {}", schema);
            // TODO: create dedicated exception.
            throw new RuntimeException(e);
        }
    }
}
