package com.mayreh.mayqb;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AliasedTableRef<T> {

    private final AliasProvider aliasProvider;

    private final TableRef<T> tableRef;
}
