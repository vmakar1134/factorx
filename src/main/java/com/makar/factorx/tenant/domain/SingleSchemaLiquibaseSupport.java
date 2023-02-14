package com.makar.factorx.tenant.domain;

import java.io.File;
import java.util.Map;
import javax.sql.DataSource;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ResourceLoader;

@Slf4j
@Setter
@Getter(value = AccessLevel.PRIVATE)
@NoArgsConstructor
public class SingleSchemaLiquibaseSupport {

    private DataSource dataSource;

    private String changeLog;

    @Getter
    private String schema;

    private ResourceLoader resourceLoader;

    private String contexts;

    private String labelFilter;

    private Map<String, String> parameters;

    private String defaultSchema;

    private String liquibaseSchema;

    private String liquibaseTablespace;

    private String databaseChangeLogTable;

    private String databaseChangeLogLockTable;

    private boolean dropFirst;

    private boolean clearCheckSums;

    private boolean shouldRun = true;

    private File rollbackFile;

    public static SingleSchemaLiquibaseSupport baseInstance(DataSource dataSource, String schema, String changeLog) {
        var liquibase = new SingleSchemaLiquibaseSupport();
        liquibase.dataSource = dataSource;
        liquibase.schema = schema;
        liquibase.changeLog = changeLog;
        return liquibase;
    }

    public void runUpdate() throws LiquibaseException {
        var springLiquibase = mapToSpringLiquibase(schema);
        springLiquibase.afterPropertiesSet();
    }

    private SpringLiquibase mapToSpringLiquibase(String schema) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDefaultSchema(schema);
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(changeLog);
        liquibase.setChangeLogParameters(parameters);
        liquibase.setContexts(contexts);
        liquibase.setLabelFilter(labelFilter);
        liquibase.setDropFirst(dropFirst);
        liquibase.setClearCheckSums(clearCheckSums);
        liquibase.setShouldRun(shouldRun);
        liquibase.setRollbackFile(rollbackFile);
        liquibase.setResourceLoader(resourceLoader);
        liquibase.setLiquibaseSchema(liquibaseSchema);
        liquibase.setLiquibaseTablespace(liquibaseTablespace);
        liquibase.setDatabaseChangeLogTable(databaseChangeLogTable);
        liquibase.setDatabaseChangeLogLockTable(databaseChangeLogLockTable);
        return liquibase;
    }

}
