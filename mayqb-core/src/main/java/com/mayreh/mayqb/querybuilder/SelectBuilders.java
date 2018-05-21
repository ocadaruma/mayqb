package com.mayreh.mayqb.querybuilder;

import com.mayreh.mayqb.*;
import com.mayreh.mayqb.util.CollectionUtil;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SelectBuilders {

    public enum SelectType {
        ALL,
        SPECIFIED_COLUMNS,
    }

    @RequiredArgsConstructor
    public static class TableSelectSQLBuilder implements SQLBuilder {
        private final List<LocalTableRef> tableRefs;
        private final SQLBlock sql;
        private final SelectType selectType;
        private final List<SQLBlock> columns;

        @Override
        public SQLBlock build() {
            SQLBlock result = SQLBlock.of("SELECT");
            switch (selectType) {
                case ALL:
                    for (LocalTableRef tableRef : tableRefs) {
                        result = result.append(SQLBlock.join(SQLBlock.of(","),
                                tableRef.aliasProvider().qualifiedAliasedColumns()));
                    }
                    break;
                case SPECIFIED_COLUMNS:
                    result = result.append(SQLBlock.join(SQLBlock.of(","), columns));
                    break;
            }
            result = result.append(sql);

            return result;
        }

        public LocalTableRef as() {
            SubQuery subQueryRef = new SubQuery(this.build(), columns);
            AliasProvider aliasProvider = new AliasProvider();

            return subQueryRef.as(aliasProvider);
        }

        private TableSelectSQLBuilder append(SQLBlock block) {
            return new TableSelectSQLBuilder(tableRefs,
                    sql.append(block), selectType, columns);
        }

        private TableSelectSQLBuilder append(LocalTableRef other, SQLBlock block) {
            return new TableSelectSQLBuilder(CollectionUtil.append(tableRefs, other),
                    sql.append(block), selectType, columns);
        }

        public TableSelectSQLBuilder leftJoin(LocalTableRef other) {
            return append(other, SQLBlock.of("LEFT JOIN ${@}", other.toSQL()));
        }

        public TableSelectSQLBuilder rightJoin(LocalTableRef other) {
            return append(other, SQLBlock.of("RIGHT JOIN ${@}", other.toSQL()));
        }

        public TableSelectSQLBuilder innerJoin(LocalTableRef other) {
            return append(other, SQLBlock.of("INNER JOIN ${@}", other.toSQL()));
        }

        public TableSelectSQLBuilder on(SQLBlock onCondition) {
            return append(SQLBlock.of("ON ${@}", onCondition));
        }

        public TableSelectSQLBuilder where(SQLBlock condition) {
            return append(SQLBlock.of("WHERE ${@}", condition));
        }

        public TableSelectSQLBuilder groupBy(SQLBlock... columns) {
            return append(SQLBlock.of("GROUP BY ${@}", SQLBlock.join(SQLBlock.of(","), columns)));
        }

        public TableSelectSQLBuilder orderBy(SQLBlock... columns) {
            return append(SQLBlock.of("ORDER BY ${@}", SQLBlock.join(SQLBlock.of(","), columns)));
        }

        public TableSelectSQLBuilder limit(int n) {
            return append(SQLBlock.of("LIMIT " + n));
        }

        public TableSelectSQLBuilder offset(int n) {
            return append(SQLBlock.of("OFFSET " + n));
        }
    }

    @RequiredArgsConstructor
    public static class SelectSQLBuilder implements SQLBuilder {
        private final List<SQLBlock> columns;

        @Override
        public SQLBlock build() {
            return SQLBlock.of("SELECT ${@}",
                    SQLBlock.join(SQLBlock.of(","), columns));
        }

        public TableSelectSQLBuilder from(LocalTableRef tableRef) {
            return new TableSelectSQLBuilder(Collections.singletonList(tableRef),
                    SQLBlock.of("FROM ${@}", tableRef.toSQL()), SelectType.SPECIFIED_COLUMNS, columns);
        }

        public LocalTableRef as(SubQueryAliasProvider sub) {
            SubQuery subQueryRef = new SubQuery(this.build(), columns);

            AliasProvider aliasProvider = new AliasProvider(
                    columns.stream().map(SQLBlock::getValue).collect(Collectors.toList()),
                    sub.localTableName());

            return subQueryRef.as(aliasProvider);
        }
    }
}
