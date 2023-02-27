package com.makar.factorx.registry.service;

import com.makar.factorx.registry.domain.liquibase.MultiSchemaLiquibaseSupport;
import com.makar.factorx.registry.domain.liquibase.SingleSchemaLiquibaseSupport;
import com.makar.factorx.registry.exception.JdbcQueryException;
import com.makar.factorx.registry.exception.LiquibaseUpdateException;
import java.sql.SQLException;
import java.util.Collection;
import javax.sql.DataSource;
import liquibase.exception.LiquibaseException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.stereotype.Service;

@Slf4j
@Getter
@Setter
@Service
@RequiredArgsConstructor
public class LiquibaseService {

    private final DataSource dataSource;

    @Qualifier("tenantProperties")
    private final LiquibaseProperties tenantProperties;

    public void runOnSchemas(Collection<String> schemas) {
        log.info("Schema based multitenancy engaged.");

        MultiSchemaLiquibaseSupport
            .baseInstance(dataSource, tenantProperties, schemas)
            .forEach(this::runUpdate);
    }

    public void runOnSchema(String schema) {
        log.info("Single schema liquibase engaged.");

        var liquibaseSupport = SingleSchemaLiquibaseSupport.baseInstance(dataSource, tenantProperties, schema);
        runUpdate(liquibaseSupport);
    }

    public void dropSchema(String schema) {
        log.info("Delete schema liquibase engaged.");

        var dropSchemaQuery = "drop schema " + schema;
        executeUpdateStatement(dropSchemaQuery);
    }

    private void runUpdate(SingleSchemaLiquibaseSupport liquibase) {
        var schema = liquibase.getSchema();
        log.info("Initializing Liquibase for schema: {}.", schema);
        createSchema(schema);
        executeUpdate(liquibase);
        log.info("Liquibase ran for schema: {}. ", schema);
    }

    private void executeUpdate(SingleSchemaLiquibaseSupport liquibase) {
        try {
            liquibase.runUpdate();
        } catch (LiquibaseException e) {
            log.error("Unable to run update on schema: {}.", liquibase.getSchema());
            throw new LiquibaseUpdateException("Unable to execute update,", e);
        }
    }

    private void createSchema(String schema) {
        var createSchemaQuery = "create schema if not exists " + schema;
        executeUpdateStatement(createSchemaQuery);
    }

    private void executeUpdateStatement(String query) {
        try (
            var connection = dataSource.getConnection();
            var statement = connection.createStatement()
        ) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            log.error("Unable to execute update query: {}", query);
            throw new JdbcQueryException("Unable to execute query,", e);
        }
    }
}
