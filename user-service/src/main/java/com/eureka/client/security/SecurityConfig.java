package com.eureka.client.security;

import com.eureka.client.entity.UserEntity;
import com.eureka.client.repository.UserRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/eureka/**").permitAll()
                        .requestMatchers("/actuator/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
//                        .requestMatchers("/users/**").hasRole("USER")
                        .requestMatchers("/user/**").permitAll()
                        .requestMatchers("/instances/**").permitAll()
                        .requestMatchers("/apps/**").permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(withDefaults())  // Enables default form login
                .httpBasic(withDefaults()); // Enables basic authentication

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepo userRepo) {
        return username -> {
            UserEntity user = userRepo.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found: " + username));

            return User.withUsername(user.getUsername())
                    .password(user.getPassword())  // Stored as hashed password
                    .roles(user.getRole().toUpperCase())  // Assuming roles are stored as "USER" or "ADMIN"
                    .build();
        };
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
