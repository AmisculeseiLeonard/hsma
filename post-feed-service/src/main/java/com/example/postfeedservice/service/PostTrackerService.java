package com.example.postfeedservice.service;

import com.example.postfeedservice.model.PostTracker;
import com.example.postfeedservice.repository.PostTrackerRepository;
import org.springframework.stereotype.Service;

@Service
public class PostTrackerService {
    private final PostTrackerRepository postTrackerRepository;

    public PostTrackerService(PostTrackerRepository postTrackerRepository) {
        this.postTrackerRepository = postTrackerRepository;
    }

    public PostTracker createPostTracker(PostTracker postTracker) {
        return postTrackerRepository.save(postTracker);
    }
}
