package com.example.postfeedservice.repository;

import com.example.postfeedservice.model.FriendshipTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
@EnableJpaRepositories
public interface FriendshipTrackerRepository extends JpaRepository<FriendshipTracker, Long> {
    List<FriendshipTracker> findByFollowerId(String followerId);
}
