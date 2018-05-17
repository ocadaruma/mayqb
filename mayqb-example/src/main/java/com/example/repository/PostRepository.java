package com.example.repository;

import com.mayreh.mayqb.TableRef;

import java.util.Arrays;
import java.util.List;

public class PostRepository extends TableRef<PostRepository.Post> {

    @Override
    public List<String> columnNames() {
        return Arrays.asList("");
    }

    public static class Post {
        private final long id;
        private final String
    }

    public List<Post> findBy()
}
