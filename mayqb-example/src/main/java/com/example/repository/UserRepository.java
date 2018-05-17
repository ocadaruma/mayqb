package com.example.repository;

import com.mayreh.mayqb.*;
import com.mayreh.mayqb.Parameters.StringParameter;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.mayreh.mayqb.QueryDSLProvider.select;

public class UserRepository implements TableRef<UserRepository.User> {

    @Value
    @Builder
    public static class User {
        long id;
        String name;
        LocalDateTime updatedAt;
        LocalDateTime createdAt;
    }

    private final AliasProvider u = aliasProvider("u");

    @Override
    public List<String> columnNames() {
        return Arrays.asList("id", "name", "updated_at", "created_at");
    }

    public User fromResultSet(WrappedResultSet rs) throws SQLException {
        return User.builder()
                .id(rs.getLong(u.column("id").getValue()))
                .name(rs.getString(u.column("name").getValue()))
                .updatedAt(null)
                .createdAt(null)
                .build();
    }

    public List<User> findAll(DBContext ctx) {
        query {
            selectFrom(this.as(u))
                    .orderBy(u.column(""))
        }.map().list().executeSync(ctx);
    }

    public List<User> findByName(String name, DBContext ctx) {
        query {
            selectFrom(this.as(u))
                    .where(u.column("name").eq(StringParameter.of(name)))
                    .orderBy(u.column("name"))
                    .limit()
        }.map().list().executeSync(ctx);
    }

    public Optional<User> findByName(String name, DBContext ctx) {
        query {
            selectFrom(this.as())
                    .where(this.name().eq(StringParameter.of(name)))
                    .orderBy(name())
                    .limit()
        }.map().single().executeSync(ctx);
    }
}
