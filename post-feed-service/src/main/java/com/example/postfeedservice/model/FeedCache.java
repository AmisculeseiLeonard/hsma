package com.example.postfeedservice.model;

import lombok.Getter;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class FeedCache {

    private static FeedCache single_instance = null;
    private final ConcurrentHashMap<String, Set<Post>> posts;

    private FeedCache() {
        posts = new ConcurrentHashMap<>();
    }

    public static synchronized FeedCache getInstance()
    {
        if (single_instance == null)
            single_instance = new FeedCache();

        return single_instance;
    }

}
