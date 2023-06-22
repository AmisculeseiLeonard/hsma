package com.healthcaresocialmedia.relationshipservice.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserEvent {
    private EventType eventType;
    private String userId;
    private String username;
    private String email;
}