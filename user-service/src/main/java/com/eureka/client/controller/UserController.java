package com.eureka.client.controller;
import com.eureka.client.entity.UserEntity;
import com.eureka.client.repository.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/add")
    public ResponseEntity<UserEntity> addUser(@RequestBody UserEntity user) {
        // Encrypt the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserEntity savedUser = userRepo.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping("/get/{username}")
    public ResponseEntity<Optional<UserEntity>> getUser(@PathVariable String username) {
        Optional<UserEntity> user = userRepo.findByUsername(username);
        if (user != null) return ResponseEntity.ok(user);
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/update/{username}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable String username, @RequestBody UserEntity updatedUser) {
        Optional<UserEntity> optionalUser = userRepo.findByUsername(username);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        UserEntity existingUser = optionalUser.get();
        // Update the user's score (and other fields as necessary)
        existingUser.setScore(updatedUser.getScore());
        userRepo.save(existingUser);
        return ResponseEntity.ok(existingUser);
    }




}
