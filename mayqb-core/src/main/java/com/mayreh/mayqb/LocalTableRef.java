package com.mayreh.mayqb;

import lombok.RequiredArgsConstructor;

import java.util.Collections;

@RequiredArgsConstructor
public class LocalTableRef {

    private final TableRef tableRef;

    private final AliasProvider aliasProvider;

    public AliasProvider aliasProvider() {
        return this.aliasProvider;
    }

    public SQLBlock toSQL() {
        return SQLBlock.of("${@} ${@}",
                tableRef.toSQL(),
                new SQLBlock(aliasProvider.localTableName(), Collections.emptyList()));
    }
}
