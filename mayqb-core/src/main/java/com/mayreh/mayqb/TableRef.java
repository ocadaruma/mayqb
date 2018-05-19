package com.mayreh.mayqb;

import java.util.List;
import java.util.stream.Collectors;

public interface TableRef<T> {

    List<String> columnNames();

    SQLBlock toSQL();

    default List<SQLBlock> columns() {
        return columnNames().stream().map(SQLBlock::of).collect(Collectors.toList());
    }

    default AliasedTableRef<T> as(AliasProvider aliasProvider) {
        return new AliasedTableRef<>(aliasProvider, this);
    }

    default AliasProvider aliasProvider(String alias) {
        return new TableAliasProvider(this, alias);
    }
}
