package com.makar.factorx.tenant.domain;

import java.util.Collection;
import java.util.Iterator;
import javax.sql.DataSource;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Getter(value = AccessLevel.PRIVATE)
@NoArgsConstructor
public class MultiSchemaLiquibaseSupport implements Iterable<SingleSchemaLiquibaseSupport> {

    private Collection<SingleSchemaLiquibaseSupport> liquibaseSupports;

    public static MultiSchemaLiquibaseSupport baseInstance(DataSource dataSource, Collection<String> schemas, String changeLog) {
        var multiSchemaSupport = new MultiSchemaLiquibaseSupport();
        var supports = schemas.stream()
            .map(schema -> SingleSchemaLiquibaseSupport.baseInstance(dataSource, schema, changeLog))
            .toList();

        multiSchemaSupport.setLiquibaseSupports(supports);
        return multiSchemaSupport;
    }

    @Override
    public Iterator<SingleSchemaLiquibaseSupport> iterator() {
        return liquibaseSupports.iterator();
    }
}
