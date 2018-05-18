package com.mayreh.mayqb;

import com.mayreh.mayqb.util.Pair;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TableAliasProvider implements AliasProvider {

    private final SQLBlock alias;

    private final Map<String, String> columnNameAliasMap;

    public TableAliasProvider(TableRef<?> tableRef, String aliasName) {
        List<String> columnNames = tableRef.columns().stream()
                .map(SQLBlock::getValue).collect(Collectors.toList());

        this.alias = SQLBlock.of(aliasName);

        // NOTE: Currently, just concaat alias and column name
        // TODO: shorten column name
        this.columnNameAliasMap = columnNames.stream()
                .map(name -> Pair.of(name, aliasName + "_" + name))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
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

        return SQLBlock.of(this.columnNameAliasMap.get(columnName));
    }
}
