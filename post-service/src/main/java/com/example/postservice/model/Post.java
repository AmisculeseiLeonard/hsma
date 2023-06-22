package com.example.postservice.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Set;

@Data
@NoArgsConstructor
@Document
@AllArgsConstructor
@Builder
public class Post {
    @Id
    private String id;
    private Instant createdAt;
    private String userId;
    private String imageUrl;
    private String description;
    private Set<Comment> comments;
    private Set<Like> likes;
}
