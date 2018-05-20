package com.mayreh.mayqb;

public interface AliasProvider {

    SQLBlock columnAlias(String columnName);

    default SQLBlock columnAlias(SQLBlock columnName) {
        return columnAlias(columnName.getValue());
    }
}
