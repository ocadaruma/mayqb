package com.mayreh.mayqb;

import lombok.Value;

@Value
public class AliasedTableRef<T> {

    AliasProvider aliasProvider;

    TableRef<T> tableRef;

    public SQLBlock toSQL() {
        return SQLBlock.of("${@} AS ${@}",
                tableRef.toSQL(),
                aliasProvider.alias());
    }
}
