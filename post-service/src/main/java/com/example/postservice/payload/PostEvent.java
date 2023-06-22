package com.example.postservice.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostEvent {
    private String postId;
    private String userId;
}
