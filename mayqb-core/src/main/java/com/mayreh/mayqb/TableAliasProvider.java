package com.mayreh.mayqb;

import com.mayreh.mayqb.util.Pair;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TableAliasProvider implements AliasProvider {

    private final Table table;

    private final String localTableName;

    private final Map<String, String> columnNameAliasMap;

    public TableAliasProvider(Table table, String localTableName) {
        this.table = table;

        this.localTableName = localTableName;

        // NOTE: Currently, just concat tableAlias and columnAlias name
        // TODO: shorten columnAlias name
        this.columnNameAliasMap = table.columnNames().stream()
                .map(name -> Pair.of(name, localTableName + "_" + name))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    @Override
    public SQLBlock columnAlias(String columnName) {
        if (!this.columnNameAliasMap.containsKey(columnName)) {
            throw new IllegalArgumentException("invalid columnAlias name : ");
        }

        return SQLBlock.of(this.columnNameAliasMap.get(columnName));
    }

    public SQLBlock qualifiedColumn(String columnName) {
        if (!this.columnNameAliasMap.containsKey(columnName)) {
            throw new IllegalArgumentException("invalid columnAlias name : ");
        }

        return SQLBlock.of("${@}.${@}",
                new SQLBlock(localTableName, Collections.emptyList()),
                new SQLBlock(columnName, Collections.emptyList()));
    }

    @Override
    public SQLBlock qualifiedAliasedColumn(String columnName) {
        return SQLBlock.of("${@} AS ${@}",
                qualifiedColumn(columnName),
                columnAlias(columnName));
    }

    @Override
    public String localTableName() {
        return this.localTableName;
    }

    @Override
    public List<String> columnNames() {
        return table.columnNames();
    }
}
