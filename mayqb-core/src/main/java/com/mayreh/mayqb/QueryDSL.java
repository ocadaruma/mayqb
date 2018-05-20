package com.mayreh.mayqb;

import com.mayreh.mayqb.querybuilder.SelectBuilders.*;

import java.util.Arrays;
import java.util.Collections;

public class QueryDSL {

    public static ResultTransformer withSQL(SQLBuilder builder) {
        return new ResultTransformer(builder.build());
    }

    public static TableSelectSQLBuilder selectFrom(AliasedTableRef tableRef) {
        return new TableSelectSQLBuilder(Collections.singletonList(tableRef),
                SQLBlock.of("FROM ${@}", tableRef.toSQL()), SelectType.ALL, Collections.emptyList());
    }

    public static SelectSQLBuilder select(SQLBlock... columns) {
        return new SelectSQLBuilder(Arrays.asList(columns));
    }
}
