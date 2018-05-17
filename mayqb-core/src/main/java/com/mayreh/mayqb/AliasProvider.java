package com.mayreh.mayqb;

public interface AliasProvider {

    SQLBlock alias();

    SQLBlock column(String columnName);
}
