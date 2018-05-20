package com.mayreh.mayqb;

import lombok.Value;

@Value
public class AliasedTableRef {

    TableAliasProvider aliasProvider;

    TableRef tableRef;

    public SQLBlock toSQL() {
        return SQLBlock.of("${@} ${@}",
                tableRef.toSQL(),
                aliasProvider.tableAlias());
    }
}
