package com.example.demo.controller;



import com.example.demo.entity.User;
import com.example.demo.service.UserService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User registeredUser = userService.register(user);
            return ResponseEntity.ok(registeredUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Optional<User> authenticatedUser = userService.authenticate(user.getEmail(), user.getPassword());
        
        if (authenticatedUser.isPresent()) {
            User loggedInUser = authenticatedUser.get();
            String welcomeMessage = "Welcome " + loggedInUser.getFirstName() + " " + loggedInUser.getLastName() + "!";
            return ResponseEntity.ok(welcomeMessage);
        } else {
            return ResponseEntity.status(401).body("Email or password incorrect. Please try again!");
        }
    }


}
