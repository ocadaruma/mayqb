package com.mayreh.mayqb;

public abstract class StandardTableRef implements TableRef {
    public abstract String tableName();

    @Override
    public SQLBlock toSQL() {
        return SQLBlock.of(tableName());
    }
}
