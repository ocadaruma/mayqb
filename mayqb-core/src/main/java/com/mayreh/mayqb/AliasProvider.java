package com.mayreh.mayqb;

import com.mayreh.mayqb.util.Pair;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AliasProvider {

    private final String localTableName;

    private final List<String> columnNames;

    private final Map<String, String> columnNameAliasMap;

    public AliasProvider(Table table, String localTableName) {
        this(table.columnNames(), localTableName);
    }

    public AliasProvider(List<String> columnNames, String localTableName) {
        this.columnNames = columnNames;
        this.localTableName = localTableName;
        // NOTE: Currently, just concat tableAlias and columnAlias name
        // TODO: shorten columnAlias name
        this.columnNameAliasMap = columnNames.stream()
                .map(name -> Pair.of(name, localTableName + "_" + name))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));

    }

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

    public SQLBlock qualifiedAliasedColumn(String columnName) {
        return SQLBlock.of("${@} AS ${@}",
                qualifiedColumn(columnName),
                columnAlias(columnName));
    }

    public String localTableName() {
        return this.localTableName;
    }

    public List<String> columnNames() {
        return this.columnNames;
    }

    public SQLBlock columnAlias(SQLBlock columnName) {
        return columnAlias(columnName.getValue());
    }

    public SQLBlock qualifiedColumn(SQLBlock columnName) {
        return qualifiedColumn(columnName.getValue());
    }

    public SQLBlock qualifiedAliasedColumn(SQLBlock columnName) {
        return qualifiedAliasedColumn(columnName.getValue());
    }

    public List<SQLBlock> qualifiedAliasedColumns() {
        return columnNames().stream()
                .map(this::qualifiedAliasedColumn).collect(Collectors.toList());
    }
}
