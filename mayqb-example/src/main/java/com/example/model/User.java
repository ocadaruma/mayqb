package com.example.model;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

/**
 * Represents a row of user table
 */
@Value
@Builder
public class User {
    long id;
    String name;
    String email;
    LocalDateTime updatedAt;
    LocalDateTime createdAt;
}
