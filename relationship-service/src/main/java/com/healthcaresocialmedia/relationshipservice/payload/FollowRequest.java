package com.healthcaresocialmedia.relationshipservice.payload;

import lombok.Data;

@Data
public class FollowRequest {
    private String followerId;
    private String followedId;
}
