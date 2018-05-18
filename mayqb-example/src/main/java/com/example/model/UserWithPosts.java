package com.example.model;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class UserWithPosts {
    User user;
    List<Post> posts;
}
