package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserRestController {

   private final UserRepository userRepository;

    public UserRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/profile_user")
    public ResponseEntity<Optional<User>> getAuthorizedUser(@AuthenticationPrincipal UserDetails userDetails) {
        Optional<User> user = userRepository.findByUsername(userDetails.getUsername());
        return ResponseEntity.ok(user);
    }
}
