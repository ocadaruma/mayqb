package com.mayreh.mayqb;

import java.util.Collections;
import java.util.List;

public abstract class Table implements TableRef {

    public abstract String tableName();

    public abstract List<String> columnNames();

    public AliasProvider aliasProvider(String localTableName) {
        return new TableAliasProvider(this, localTableName);
    }

    public LocalTableRef as(AliasProvider aliasProvider) {
        return new LocalTableRef(this, aliasProvider);
    }

    @Override
    public SQLBlock toSQL() {
        return new SQLBlock(tableName(), Collections.emptyList());
    }
}
