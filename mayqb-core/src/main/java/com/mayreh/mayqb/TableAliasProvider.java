package com.mayreh.mayqb;

import com.mayreh.mayqb.util.Pair;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TableAliasProvider implements AliasProvider {

    private final SQLBlock tableAlias;

    private final Map<String, String> columnNameAliasMap;

    public TableAliasProvider(TableRef tableRef, String aliasName) {
        List<String> columnNames = tableRef.columns().stream()
                .map(SQLBlock::getValue).collect(Collectors.toList());

        this.tableAlias = SQLBlock.of(aliasName);

        // NOTE: Currently, just concat tableAlias and columnAlias name
        // TODO: shorten columnAlias name
        this.columnNameAliasMap = columnNames.stream()
                .map(name -> Pair.of(name, aliasName + "_" + name))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    public SQLBlock tableAlias() {
        return this.tableAlias;
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

        return SQLBlock.of("${@}.${@}", tableAlias, SQLBlock.of(columnName));
    }

    public SQLBlock qualifiedColumnWithAlias(String columnName) {
        return SQLBlock.of("${@} AS ${@}",
                qualifiedColumn(columnName),
                columnAlias(columnName));
    }
}
