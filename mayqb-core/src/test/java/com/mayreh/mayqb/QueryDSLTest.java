package com.mayreh.mayqb;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.mayreh.mayqb.QueryDSL.*;
import static org.assertj.core.api.Assertions.*;

public class QueryDSLTest {

    public static class UserTable extends Table {

        public final AliasProvider u = aliasProvider("u");

        @Override
        public String tableName() {
            return "user";
        }

        @Override
        public List<String> columnNames() {
            return Arrays.asList("id", "name", "created_at");
        }
    }

    public static class PostTable extends Table {

        public final AliasProvider p = aliasProvider("p");

        @Override
        public String tableName() {
            return "post";
        }

        @Override
        public List<String> columnNames() {
            return Arrays.asList("id", "user_id", "content", "created_at");
        }
    }

    @Test
    public void buildSelectOne() {
        SQLBlock result = select(SQLBlock.of("1")).build();
        assertThat(result).isEqualTo(SQLBlock.of("SELECT 1"));
    }

    @Test
    public void buildSelect() {
        UserTable userTable = new UserTable();
        SQLBlock result = select(SQLBlock.of("name"), SQLBlock.of("COUNT(1)"))
                .from(userTable.as(userTable.u))
                .groupBy(userTable.u.qualifiedColumn("name")).build();

        assertThat(result).isEqualTo(SQLBlock.of("SELECT name,COUNT(1) FROM user u GROUP BY u.name"));
    }

    @Test
    public void buildSelectFrom() {
        UserTable userTable = new UserTable();
        SQLBlock result = selectFrom(userTable.as(userTable.u))
                .orderBy(
                        userTable.u.qualifiedColumn("id"),
                        userTable.u.qualifiedColumn("name"))
                .limit(1)
                .offset(55301)
                .build();

        assertThat(result).isEqualTo(SQLBlock.of(
                "SELECT u.id AS u_id,u.name AS u_name,u.created_at AS u_created_at FROM user u ORDER BY u.id,u.name LIMIT 1 OFFSET 55301"));
    }
}
