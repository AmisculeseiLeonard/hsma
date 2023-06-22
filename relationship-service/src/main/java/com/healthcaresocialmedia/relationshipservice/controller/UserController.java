package com.healthcaresocialmedia.relationshipservice.controller;

import com.healthcaresocialmedia.relationshipservice.model.User;
import com.healthcaresocialmedia.relationshipservice.payload.FollowRequest;
import com.healthcaresocialmedia.relationshipservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/relationship")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @PostMapping("/user")
//    public User createUser(@RequestBody User user) {
//       return userService.createUser(user);
//    }

    @PostMapping("/follow")
    public ResponseEntity<User> follow(@RequestBody FollowRequest followRequest) {
        return ResponseEntity.ok(userService.follow(followRequest));
    }

    @GetMapping("/followers/{userId}")
    public List<User> findFollowers(@PathVariable String userId) {
        return userService.findFollowers(userId);
    }

    @GetMapping("/following/{userId}")
    public List<User> findFollowing(@PathVariable String userId) {
        return userService.findFollowing(userId);
    }
}
