package com.mayreh.mayqb;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TableAliasProvider implements AliasProvider {

    private final SQLBlock alias;

    private final List<String> columnNames;

    private final Map<String, String> columnNameAliasMap;

    public TableAliasProvider(TableRef<?> tableRef, String aliasName) {
        this.columnNames = tableRef.columns().stream()
                .map(SQLBlock::getValue).collect(Collectors.toList());
        this.alias = SQLBlock.of(aliasName);
    }

    @Override
    public SQLBlock alias() {
        return this.alias;
    }

    @Override
    public SQLBlock column(String columnName) {
        if (!this.columnNameAliasMap.containsKey(columnName)) {
            throw new IllegalArgumentException("invalid column name : ");
        }

        return null;
    }
}
