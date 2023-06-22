package com.example.appointmentservice.service;

import com.example.appointmentservice.exception.ResourceNotFoundException;
import com.example.appointmentservice.model.User;
import com.example.appointmentservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    public User findUser(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid user ID"));
    }
}
