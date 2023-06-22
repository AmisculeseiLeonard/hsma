package com.example.postfeedservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import java.time.Instant;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    private String id;
    private Instant createdAt;
    private String userId;
    private String imageUrl;
    private String description;
    private Set<Comment> comments;
    private Set<Like> likes;
}
