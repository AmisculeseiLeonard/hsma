package com.example.postservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostsRequestEvent {
    private List<String> postsIds;
    private String followerId;
}
