
package com.helpdesk.helpdesk.controller;

import com.helpdesk.helpdesk.entity.User;
import com.helpdesk.helpdesk.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody User user) {

        User registeredUser = authService.register(user);

        if (registeredUser == null) {
            return ResponseEntity.status(409)
                    .body("Email already exists");
        }

        return ResponseEntity.status(201)
                .body(registeredUser);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {

        System.out.println("CONTROLLER DEBUG: Email = "
                + user.getEmail());

        System.out.println("CONTROLLER DEBUG: Password is null = "
                + (user.getPassword() == null));

        User loggedInUser = authService.login(
                user.getEmail(),
                user.getPassword()
        );

        if (loggedInUser == null) {
            return ResponseEntity.status(401)
                    .body("Invalid email or password");
        }

        return ResponseEntity.ok(loggedInUser);
    }

    // Get all registered users
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return authService.getAllUsers();
    }
}
