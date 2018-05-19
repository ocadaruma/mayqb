package com.mayreh.mayqb;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

public class QueryDSL {

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class TableSelectSQLBuilder<T> implements SQLBuilder {
        private final AliasedTableRef<T> tableRef;
        private final SQLBlock sql;

        @Override
        public SQLBlock build() {
            return sql;
        }

        private TableSelectSQLBuilder<T> append(SQLBlock block) {
            return new TableSelectSQLBuilder<>(tableRef, sql.append(block));
        }

        public TableSelectSQLBuilder<T> leftJoin(AliasedTableRef<T> other) {
            return append(SQLBlock.of("LEFT JOIN ${@}", other.toSQL()));
        }

        public TableSelectSQLBuilder<T> rightJoin(AliasedTableRef<T> other) {
            return append(SQLBlock.of("RIGHT JOIN ${@}", other.toSQL()));
        }

        public TableSelectSQLBuilder<T> innerJoin(AliasedTableRef<T> other) {
            return append(SQLBlock.of("INNER JOIN ${@}", other.toSQL()));
        }

        public TableSelectSQLBuilder<T> on(SQLBlock onCondition) {
            return append(SQLBlock.of("ON ${@}", onCondition));
        }

        public TableSelectSQLBuilder<T> where(SQLBlock condition) {
            return append(SQLBlock.of("WHERE ${@}", condition));
        }

        public TableSelectSQLBuilder<T> limit(int n) {
            return append(SQLBlock.of("LIMIT " + n));
        }

        public TableSelectSQLBuilder<T> offset(int n) {
            return append(SQLBlock.of("OFFSET " + n));
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class SelectSQLBuilder implements SQLBuilder {
        private final SQLBlock sql;

        @Override
        public SQLBlock build() {
            return sql;
        }

        public <T> TableSelectSQLBuilder<T> from(AliasedTableRef<T> tableRef) {
            return new TableSelectSQLBuilder<>(tableRef, )
        }
    }

    public static ResultTransformer withSQL(SQLBuilder builder) {
        return new ResultTransformer(builder.build());
    }

    public static <T> TableSelectSQLBuilder selectFrom(AliasedTableRef<T> tableRef) {
        return new TableSelectSQLBuilder<>(tableRef, SQLBlock.of("SELECT"));
    }

    public static <T> TableSelectSQLBuilder select(SQLBlock... columns) {
        return new TableSelectSQLBuilder<>(tableRef, SQLBlock.of("SELECT"));
    }
}
