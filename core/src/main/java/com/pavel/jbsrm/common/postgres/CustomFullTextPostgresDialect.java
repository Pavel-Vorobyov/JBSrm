package com.pavel.jbsrm.common.postgres;

import org.hibernate.dialect.PostgreSQL94Dialect;

public class CustomFullTextPostgresDialect extends PostgreSQL94Dialect {

    public CustomFullTextPostgresDialect() {
        super();
        registerFunction("fts", new PostgreSQLFullTextSearchFunction());
    }
}
