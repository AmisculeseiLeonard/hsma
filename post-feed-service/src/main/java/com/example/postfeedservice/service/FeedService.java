package com.example.postfeedservice.service;

import com.example.postfeedservice.events.PostsRequestEvent;
import com.example.postfeedservice.messaging.FeedRequestSender;
import com.example.postfeedservice.model.FeedCache;
import com.example.postfeedservice.model.Post;
import com.example.postfeedservice.repository.FriendshipTrackerRepository;
import com.example.postfeedservice.repository.PostTrackerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Service
@Slf4j
public class FeedService {

    private final FeedCache feedCache;
    private final FriendshipTrackerRepository friendshipTrackerRepository;
    private final PostTrackerRepository postTrackerRepository;
    private final FeedRequestSender feedRequestSender;

    public FeedService(FriendshipTrackerRepository friendshipTrackerRepository, PostTrackerRepository postTrackerRepository, FeedRequestSender feedRequestSender) {
        this.friendshipTrackerRepository = friendshipTrackerRepository;
        this.postTrackerRepository = postTrackerRepository;
        this.feedRequestSender = feedRequestSender;
        feedCache = FeedCache.getInstance();
    }

    public void generateFeed(String userId) {
        List<String> followingIds = friendshipTrackerRepository.findByFollowerId(userId)
                .stream()
                .flatMap(friendshipTracker ->
                        Stream.of(friendshipTracker.getFollowedId()))
                .toList();

        List<String> postsIds = new ArrayList<>();
        followingIds.forEach(followingId -> postTrackerRepository.findByUserId(followingId)
                        .forEach(postTracker -> postsIds.add(postTracker.getPostId())));

        PostsRequestEvent postsRequestEvent = new PostsRequestEvent(postsIds, userId);
        try {
            feedRequestSender.sendFeedRequest(postsRequestEvent);
        } catch (JsonProcessingException e) {
            log.error("Error occurred while trying to send message", e);
            throw new MessagingException("Error occurred while trying to send message");
        }
        postsIds.forEach(System.out::println);
    }

    public Set<Post> getFeed(String followerId) {
        return feedCache.getPosts().get(followerId);
    }
}
