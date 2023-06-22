package com.healthcaresocialmedia.relationshipservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.healthcaresocialmedia.relationshipservice.exception.MessagingException;
import com.healthcaresocialmedia.relationshipservice.exception.ResourceNotFoundException;
import com.healthcaresocialmedia.relationshipservice.events.FollowEvent;
import com.healthcaresocialmedia.relationshipservice.messaging.FollowEventSender;
import com.healthcaresocialmedia.relationshipservice.model.Friendship;
import com.healthcaresocialmedia.relationshipservice.model.User;
import com.healthcaresocialmedia.relationshipservice.payload.FollowRequest;
import com.healthcaresocialmedia.relationshipservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final FollowEventSender followEventSender;

    public UserService(UserRepository userRepository, FollowEventSender followEventSender) {
        this.userRepository = userRepository;
        this.followEventSender = followEventSender;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User follow(FollowRequest followRequest){
        User follower = userRepository.findById(followRequest.getFollowerId())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid user ID"));

        User followed = userRepository.findById(followRequest.getFollowedId())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid user ID"));

        if(follower.getFriendships() == null) {
            followed.setFriendships(new HashSet<>());
        }

        Friendship friendship = new Friendship();
        friendship.setUserFollowed(followed);
        follower.getFriendships().add(friendship);

        User savedFollower = userRepository.save(follower);

        FollowEvent followEvent = new FollowEvent();
        followEvent.setFollowedId(followed.getId());
        followEvent.setFollowerId(follower.getId());
        try {
            followEventSender.sendFollowEvent(followEvent);
        } catch (JsonProcessingException e) {
            log.error("Error occurred while trying to send a message", e);
            throw new MessagingException("Error occurred while trying to send a message");
        }

        return savedFollower;
    }

    public List<User> findFollowers(String userId) {
        return userRepository.findFollowers(userId);
    }

    public List<User> findFollowing(String userId) {
        return userRepository.findFollowing(userId);
    }
}
