package com.example.postfeedservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class PostTracker {
    @Id
    @GeneratedValue
    private long id;
    private String postId;
    private String userId;
}
