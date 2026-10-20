
package com.helpdesk.helpdesk.service;

import com.helpdesk.helpdesk.entity.User;
import com.helpdesk.helpdesk.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Objects;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Register a new user
    public User register(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            return null;
        }

        // Public registration always creates a USER
        user.setRole("USER");

        return userRepository.save(user);
    }

    public User login(String email, String password) {

        System.out.println("LOGIN DEBUG: Email received = " + email);

        Optional<User> optionalUser =
                userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            System.out.println("LOGIN DEBUG: User not found");
            return null;
        }

        User user = optionalUser.get();

        System.out.println("LOGIN DEBUG: User found");
        System.out.println("LOGIN DEBUG: Stored password is null = "
                + (user.getPassword() == null));
        System.out.println("LOGIN DEBUG: Submitted password is null = "
                + (password == null));

        if (user.getPassword() == null || password == null) {
            System.out.println("LOGIN DEBUG: Password is null");
            return null;
        }

        boolean matches = user.getPassword().equals(password);

        System.out.println("LOGIN DEBUG: Password matches = " + matches);

        if (matches) {
            return user;
        }

        return null;
    }
    // Get all registered users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
