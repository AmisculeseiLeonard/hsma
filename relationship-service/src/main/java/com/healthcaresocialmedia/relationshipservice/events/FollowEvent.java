package com.healthcaresocialmedia.relationshipservice.events;

import lombok.Data;

@Data
public class FollowEvent {
    private String followerId;
    private String followedId;
}
