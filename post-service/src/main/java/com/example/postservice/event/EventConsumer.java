package com.example.postservice.event;

import com.example.postservice.messaging.PostEventSender;
import com.example.postservice.model.Post;
import com.example.postservice.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Slf4j
@Component
public class EventConsumer {

    private final PostService postService;
    private final ObjectMapper objectMapper;
    private final PostEventSender postEventSender;

    public EventConsumer(PostService postService, ObjectMapper objectMapper, PostEventSender postEventSender) {
        this.postService = postService;
        this.objectMapper = objectMapper;
        this.postEventSender = postEventSender;
    }

    @Bean
    public Consumer<Message<String>> postConsumer() {
        return message -> {
            log.info("Received new message from Kafka topic" + message.getPayload());
            try{
                if(Objects.equals(message.getHeaders().get("type"), "postsRequest")){
                    PostsRequestEvent postsRequestEvent = objectMapper.readValue(message.getPayload(), PostsRequestEvent.class);
                    Set<Post> posts = new HashSet<>(postService.getPostsByIds(postsRequestEvent.getPostsIds()));
                    postEventSender.sendPostsEvent(posts, postsRequestEvent.getFollowerId());
                }else{
                    log.info("Unknown message type" + message);
                }
            }catch (Exception e) {
                log.error("Error occurred while trying to read the message", e);
            }
        };
    }
}
