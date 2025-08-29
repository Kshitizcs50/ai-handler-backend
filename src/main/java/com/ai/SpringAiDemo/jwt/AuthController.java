package com.ai.SpringAiDemo.jwt;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;

import com.ai.SpringAiDemo.jwt.User;
import com.ai.SpringAiDemo.Repository.UserRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/signup")
    public Map<String, String> signup(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword())); // hash password
        userRepository.save(user);

        return Collections.singletonMap("message", "User registered successfully");
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> user) {
        User dbUser = userRepository.findByUsername(user.get("username"));

        if (dbUser != null && passwordEncoder.matches(user.get("password"), dbUser.getPassword())) {
            String token = jwtUtil.generateToken(dbUser.getUsername());
            return Collections.singletonMap("token", token);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
    }
}
