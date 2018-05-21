package com.mayreh.mayqb;

import lombok.Value;

import java.util.List;

@Value
public class SubQuery implements TableRef {

    SQLBlock sql;

    List<SQLBlock> columns;

    public LocalTableRef as(AliasProvider aliasProvider) {
        return new LocalTableRef(this, aliasProvider);
    }

    @Override
    public SQLBlock toSQL() {
        return sql;
    }
}
