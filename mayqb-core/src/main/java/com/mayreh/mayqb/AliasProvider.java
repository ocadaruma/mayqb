package com.mayreh.mayqb;

import java.util.List;
import java.util.stream.Collectors;

public interface AliasProvider {

    SQLBlock columnAlias(String columnName);

    SQLBlock qualifiedColumn(String columnName);

    SQLBlock qualifiedAliasedColumn(String columnName);

    String localTableName();

    List<String> columnNames();

    default SQLBlock columnAlias(SQLBlock columnName) {
        return columnAlias(columnName.getValue());
    }

    default SQLBlock qualifiedColumn(SQLBlock columnName) {
        return qualifiedColumn(columnName.getValue());
    }

    default SQLBlock qualifiedAliasedColumn(SQLBlock columnName) {
        return qualifiedAliasedColumn(columnName.getValue());
    }

    default List<SQLBlock> qualifiedAliasedColumns() {
        return columnNames().stream()
                .map(this::qualifiedAliasedColumn).collect(Collectors.toList());
    }
}
