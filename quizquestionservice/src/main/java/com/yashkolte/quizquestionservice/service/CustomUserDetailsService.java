package com.yashkolte.quizquestionservice.service;

import com.yashkolte.quizquestionservice.dto.UserDto;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final RestTemplate restTemplate;
    private final PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(RestTemplate restTemplate, PasswordEncoder passwordEncoder) {
        this.restTemplate = restTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Call User Microservice to get user details
        String url = "http://localhost:8081/user/get/" + username;
        UserDto user = restTemplate.getForObject(url, UserDto.class);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword()) // Password already encoded
                .authorities(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
                .build();
    }
}
