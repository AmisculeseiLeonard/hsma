package com.example.postservice.messaging;

import com.example.postservice.event.PostResponseEvent;
import com.example.postservice.exception.MessagingException;
import com.example.postservice.model.Post;
import com.example.postservice.payload.PostEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
@Configuration
public class PostEventSender {
    private final StreamBridge streamBridge;
    private final ObjectMapper objectMapper;

    public PostEventSender(StreamBridge streamBridge, ObjectMapper objectMapper) {
        this.streamBridge = streamBridge;
        this.objectMapper = objectMapper;
    }

    public void sendPostEvent(Post post) throws JsonProcessingException {
        String channel = "postChannel";
        Message<String> message = MessageBuilder
                .withPayload(objectMapper.writeValueAsString(covert(post)))
                .setHeader("type", "postEvent")
                .build();

        streamBridge.send(channel, message);
    }

    public void sendPostsEvent(Set<Post> posts, String followerId) {
        String channel = "postsResponseChannel";
        PostResponseEvent postResponseEvent = new PostResponseEvent(posts, followerId);
        Message<String> message = null;
        try {
            message = MessageBuilder
                    .withPayload(objectMapper.writeValueAsString(postResponseEvent))
                    .setHeader("type", "postResponseEvent")
                    .build();
        } catch (JsonProcessingException e) {
            log.error("Error occurred while trying to send the message", e);
            throw new MessagingException("Error occurred while trying to send the message");
        }

        streamBridge.send(channel, message);
    }

    private PostEvent covert(Post post) {
        return PostEvent.builder()
                .postId(post.getId())
                .userId(post.getUserId())
                .build();
    }


}
