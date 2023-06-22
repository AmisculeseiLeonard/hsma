package com.healthcaresocialmedia.UserManagementService.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.healthcaresocialmedia.UserManagementService.exception.EmailAlreadyRegisteredException;
import com.healthcaresocialmedia.UserManagementService.exception.MessagingException;
import com.healthcaresocialmedia.UserManagementService.exception.ResourceNotFoundException;
import com.healthcaresocialmedia.UserManagementService.exception.UsernameAlreadyRegisteredException;
import com.healthcaresocialmedia.UserManagementService.messaging.UserEventSender;
import com.healthcaresocialmedia.UserManagementService.model.User;
import com.healthcaresocialmedia.UserManagementService.payload.EventType;
import com.healthcaresocialmedia.UserManagementService.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserEventSender userEventSender;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, UserEventSender userEventSender, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.userEventSender = userEventSender;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public User registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new EmailAlreadyRegisteredException("Email already registered");
        }
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UsernameAlreadyRegisteredException("Username already registered");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActivated(false);
        User savedUser = userRepository.save(user);
        try {
            userEventSender.sendUserEvent(savedUser, EventType.USER_CREATED);
        } catch (JsonProcessingException e) {
            throw new MessagingException("Error occurred while trying to send event");
        }
        return savedUser;
    }

    @Transactional
    public void activateAccount(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid user ID"));
        user.setActivated(true);
        User savedUser = userRepository.save(user);
        try {
            userEventSender.sendUserEvent(savedUser, EventType.USER_ACCOUNT_CONFIRMED);
        } catch (JsonProcessingException e) {
            throw new MessagingException("Error occurred while trying to send message");
        }

    }

    public List<User> findHealthcareSpecialists(String country, String city, String domain) {
        return userRepository.findHealthcareSpecialist(country, city, domain);
    }

    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }

//    public User updateUser(String id, User user) {
//        User existingUser = userRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Invalid customer ID"));
//
//    }


//    public Customer getCustomerById(long id) {
//        return customerRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException(String.format("Customer with id %d not found", id)));
//    }
}
