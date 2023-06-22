package com.example.postservice.event;

import com.example.postservice.model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseEvent {
    private Set<Post> posts;
    private String followerId;
}
