package com.example.postfeedservice.repository;

import com.example.postfeedservice.model.PostTracker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostTrackerRepository extends JpaRepository<PostTracker, Long> {
    List<PostTracker> findByUserId(String userId);
}
