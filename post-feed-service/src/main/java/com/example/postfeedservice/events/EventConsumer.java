package com.example.postfeedservice.events;

import com.example.postfeedservice.model.FeedCache;
import com.example.postfeedservice.model.FriendshipTracker;
import com.example.postfeedservice.model.PostTracker;
import com.example.postfeedservice.service.FriendshipTrackerService;
import com.example.postfeedservice.service.PostTrackerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Consumer;

@Slf4j
@Component
public class EventConsumer {

    private final FriendshipTrackerService friendshipTrackerService;
    private final PostTrackerService postTrackerService;
    private final ObjectMapper objectMapper;

    public EventConsumer(FriendshipTrackerService friendshipTrackerService, PostTrackerService postTrackerService, ObjectMapper objectMapper) {
        this.friendshipTrackerService = friendshipTrackerService;
        this.postTrackerService = postTrackerService;
        this.objectMapper = objectMapper;
    }


    @Bean
    public Consumer<Message<String>> feedConsumer() {
        return message -> {
            log.info("Received new message from Kafka topic" + message.getPayload());
            try{
                if(Objects.equals(message.getHeaders().get("type"), "postEvent")){
                    PostEvent postEvent = objectMapper.readValue(message.getPayload(), PostEvent.class);
                    PostTracker postTracker = covertToPostTracker(postEvent);
                    postTrackerService.createPostTracker(postTracker);
                } else if (Objects.equals(message.getHeaders().get("type"), "followEvent")) {
                    FollowEvent followEvent =  objectMapper.readValue(message.getPayload(), FollowEvent.class);
                    FriendshipTracker friendshipTracker = covertToFriendshipTracker(followEvent);
                    friendshipTrackerService.createFriendshipTracker(friendshipTracker);
                } else if (Objects.equals(message.getHeaders().get("type"), "postResponseEvent")) {
                    PostResponseEvent postResponseEvent = objectMapper.readValue(message.getPayload(), PostResponseEvent.class);
                    FeedCache.getInstance().getPosts().put(postResponseEvent.getFollowerId(), postResponseEvent.getPosts());
                } else{
                    log.info("Unknown message type" + message);
                }
            }catch (Exception e) {
                log.error("Error occurred while trying to read the message", e);
            }
        };
    }

    private PostTracker covertToPostTracker(PostEvent postEvent){
        PostTracker postTracker = new PostTracker();
        postTracker.setPostId(postEvent.getPostId());
        postTracker.setUserId(postEvent.getUserId());
        return postTracker;
    }

    private FriendshipTracker covertToFriendshipTracker(FollowEvent followEvent) {
        FriendshipTracker friendshipTracker = new FriendshipTracker();
        friendshipTracker.setFollowedId(followEvent.getFollowedId());
        friendshipTracker.setFollowerId(followEvent.getFollowerId());
        return friendshipTracker;
    }
}
