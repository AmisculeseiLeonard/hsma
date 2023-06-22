package com.healthcaresocialmedia.UserManagementService.service;

import com.healthcaresocialmedia.UserManagementService.config.CustomUserDetails;
import com.healthcaresocialmedia.UserManagementService.exception.ResourceNotFoundException;
import com.healthcaresocialmedia.UserManagementService.model.User;
import com.healthcaresocialmedia.UserManagementService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(CustomUserDetails::new).orElseThrow(() -> new ResourceNotFoundException("User with name: " + username + " was not found"));
    }
}
