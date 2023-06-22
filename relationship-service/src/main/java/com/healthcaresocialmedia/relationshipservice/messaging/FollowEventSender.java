package com.healthcaresocialmedia.relationshipservice.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthcaresocialmedia.relationshipservice.events.FollowEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Configuration
public class FollowEventSender {

    private final StreamBridge streamBridge;
    private final ObjectMapper objectMapper;

    public FollowEventSender(StreamBridge streamBridge, ObjectMapper objectMapper) {
        this.streamBridge = streamBridge;
        this.objectMapper = objectMapper;
    }

    public void sendFollowEvent(FollowEvent followEvent) throws JsonProcessingException {
        String channel = "followChannel";
        Message<String> message = MessageBuilder
                .withPayload(objectMapper.writeValueAsString(followEvent))
                .setHeader("type", "followEvent")
                .build();
        streamBridge.send(channel, message);
    }
}
