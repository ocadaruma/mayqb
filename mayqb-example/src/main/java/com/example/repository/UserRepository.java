package com.example.repository;

import com.example.model.User;
import com.example.model.UserWithPosts;
import com.mayreh.mayqb.*;
import com.mayreh.mayqb.Parameters.StringParameter;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.mayreh.mayqb.QueryDSL.selectFrom;
import static com.mayreh.mayqb.QueryDSL.withSQL;

@RequiredArgsConstructor
public class UserRepository extends Table {

    public final AliasProvider u = aliasProvider("u");

    private final PostRepository postRepository;

    @Override
    public String tableName() {
        return "user";
    }

    @Override
    public List<String> columnNames() {
        return Arrays.asList("id", "name", "email", "updated_at", "created_at");
    }

    public User from(WrappedResultSet rs) throws SQLException {
        return User.builder()
                .id(rs.getLong(u.columnAlias("id").getValue()))
                .name(rs.getString(u.columnAlias("name").getValue()))
                .email(rs.getString(u.columnAlias("email").getValue()))
                .updatedAt(null)
                .createdAt(null)
                .build();
    }

    public List<UserWithPosts> findAll(DBContext ctx) {
        selectFrom(this.as(u))
                .orderBy(u.columnAlias("id"))
                .one(UserRepository::from)
                .toMany(postRepository::optionalFrom)
                .map(UserWithPosts::new)
                .list()
                .executeSync(ctx);
    }

    public List<User> findByName(String name, int limit, DBContext ctx) {
        SubQuery sub = SubQuery.named("sub");
        withSQL(
                selectFrom(
                        selectFrom(this.as(u))
                                .innerJoin(postRepository.as(postRepository.p))
                                .orderBy(u.qualifiedColumn("name").asc())
                                .as(sub)
                )
                        .innerJoin(postRepository.as(postRepository.p))
                        .orderBy(u.inSubQuery(sub).qualifiedColumn("name").asc())
        )
                .one(this::from)
                .toMany(postRepository::optionalFrom)
                .map(UserWithPosts::new)
                .list()
                .executeSync(ctx);

        selectFrom(this.as(u))
                .where(u.columnAlias("name").eq(StringParameter.of(name)))
                .orderBy(u.columnAlias("name").asc())
                .map()
                .list()
                .executeSync(ctx);

        Optional<User> user = withSQL(selectFrom(this.as(u)))
                .transform(this::from)
                .single()
                .executeSync(ctx);
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
