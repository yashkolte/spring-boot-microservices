package com.eureka.client.controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private List<String> users = new ArrayList<>(Arrays.asList("Alice", "Bob", "Charlie"));

    @GetMapping("/all")
    public List<String> getUsers() {
        return users;
    }

    @PostMapping("/add")
    public String addUser(@RequestParam String name) {
        users.add(name);
        return "User added successfully!";
    }
}
