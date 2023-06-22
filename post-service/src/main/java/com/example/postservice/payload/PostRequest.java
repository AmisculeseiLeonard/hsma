package com.example.postservice.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostRequest {
    private String userId;
    private String imageUrl;
    private String description;
}
