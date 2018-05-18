package com.example.model;

import lombok.Builder;
import lombok.Value;

/**
 * Represents a row of post table
 */
@Value
@Builder
public class Post {
    private final long id;
    private final long userId;
    private final String body;
}
