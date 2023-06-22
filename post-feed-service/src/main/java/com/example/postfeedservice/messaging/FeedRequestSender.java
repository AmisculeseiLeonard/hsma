package com.example.postfeedservice.messaging;

import com.example.postfeedservice.events.PostsRequestEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FeedRequestSender {
    private final StreamBridge streamBridge;
    private final ObjectMapper objectMapper;

    public FeedRequestSender(StreamBridge streamBridge, ObjectMapper objectMapper) {
        this.streamBridge = streamBridge;
        this.objectMapper = objectMapper;
    }

    public void sendFeedRequest(PostsRequestEvent postsRequestEvent) throws JsonProcessingException {
        String channel = "postsRequestChannel";
        Message<String> message = MessageBuilder
                .withPayload(objectMapper.writeValueAsString(postsRequestEvent))
                .setHeader("type", "postsRequest")
                .build();

        streamBridge.send(channel, message);
    }
}
