package com.example.postfeedservice.service;

import com.example.postfeedservice.model.FriendshipTracker;
import com.example.postfeedservice.repository.FriendshipTrackerRepository;
import org.springframework.stereotype.Service;

@Service
public class FriendshipTrackerService {

    private final FriendshipTrackerRepository friendshipTrackerRepository;

    public FriendshipTrackerService(FriendshipTrackerRepository friendshipTrackerRepository) {
        this.friendshipTrackerRepository = friendshipTrackerRepository;
    }

    public FriendshipTracker createFriendshipTracker(FriendshipTracker friendshipTracker) {
        return friendshipTrackerRepository.save(friendshipTracker);
    }
}
