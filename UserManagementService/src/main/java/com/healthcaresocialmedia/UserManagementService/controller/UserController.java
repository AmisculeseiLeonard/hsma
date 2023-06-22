package com.healthcaresocialmedia.UserManagementService.controller;

import com.healthcaresocialmedia.UserManagementService.exception.UserAuthenticationFailedException;
import com.healthcaresocialmedia.UserManagementService.model.User;
import com.healthcaresocialmedia.UserManagementService.payload.AuthRequest;
import com.healthcaresocialmedia.UserManagementService.service.UserService;
import jakarta.ws.rs.QueryParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest){
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if(authenticate.isAuthenticated()){
            return userService.generateToken(authRequest.getUsername());
        }else{
            throw new UserAuthenticationFailedException("Access denied");
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token){
        userService.validateToken(token);
        return "Token is valid";
    }

    @GetMapping("/activateAccount")
    public String activateAccount(@RequestParam String id) {
        userService.activateAccount(id);
        return "Account activated successfully";
    }

    @GetMapping("/findHealthcareSpecialists")
    public List<User> queryHealthcareSpecialists(
            @QueryParam("country") String country,
            @QueryParam("city") String city,
            @QueryParam("domain") String domain
    ) {
        return userService.findHealthcareSpecialists(country, city, domain);
    }

//    @PostMapping("/updateUser/{id}")
//    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) {
//        return ResponseEntity.ok(userService.updateUser(id, user));
//    }

}
