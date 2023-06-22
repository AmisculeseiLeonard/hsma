package com.example.postfeedservice.controller;

import com.example.postfeedservice.model.Post;
import com.example.postfeedservice.service.FeedService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/feed")
public class FeedController {

    private final FeedService feedService;

    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    @PostMapping("/generateFeed/{userId}")
    public void generateFeed(@PathVariable String userId) {
        feedService.generateFeed(userId);
    }

    @GetMapping("/{userId}")
    public Set<Post> getFeed(@PathVariable String userId) {
        return feedService.getFeed(userId);
    }
}
