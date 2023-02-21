package com.makar.factorx.registry.domain.liquibase;

import java.util.Collection;
import java.util.Iterator;
import javax.sql.DataSource;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;

@Slf4j
@Setter(value = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MultiSchemaLiquibaseSupport implements Iterable<SingleSchemaLiquibaseSupport> {

    private Collection<SingleSchemaLiquibaseSupport> liquibaseSupports;

    public static MultiSchemaLiquibaseSupport baseInstance(DataSource dataSource, LiquibaseProperties liquibaseProperties, Collection<String> schemas) {
        var multiSchemaSupport = new MultiSchemaLiquibaseSupport();
        var supports = schemas.stream()
            .map(schema -> SingleSchemaLiquibaseSupport.baseInstance(dataSource, liquibaseProperties, schema))
            .toList();

        multiSchemaSupport.setLiquibaseSupports(supports);
        return multiSchemaSupport;
    }

    @Override
    public Iterator<SingleSchemaLiquibaseSupport> iterator() {
        return liquibaseSupports.iterator();
    }
}
