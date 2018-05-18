package com.example.repository;

import com.example.model.Post;
import com.mayreh.mayqb.AliasProvider;
import com.mayreh.mayqb.TableRef;
import com.mayreh.mayqb.WrappedResultSet;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PostRepository implements TableRef<Post> {

    public final AliasProvider p = aliasProvider("p");

    @Override
    public List<String> columnNames() {
        return Arrays.asList("id", "user_id", "body");
    }

    public Post from(WrappedResultSet rs) throws SQLException {
        return Post.builder()
                .id(rs.getLong(p.column("id").getValue()))
                .userId(rs.getLong(p.column("user_id").getValue()))
                .body(rs.getString(p.column("body").getValue()))
                .build();
    }

    public Optional<Post> optionalFrom(WrappedResultSet rs) throws SQLException {
        return rs.getLongOpt(p.column("id").getValue())
                .map(unused -> from(rs)).toOptional();
    }
}
