package com.example.postservice.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentRequest {
    private String postId;
    private String userId;
    private String username;
    private String commentText;
}
