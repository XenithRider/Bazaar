package com.example.ecobazaar.service;


import com.example.ecobazaar.config.JwtUtil;
import com.example.ecobazaar.dto.LoginRequest;
import com.example.ecobazaar.dto.RegisterRequest;
import com.example.ecobazaar.dto.UserResponse;
import com.example.ecobazaar.model.User;
import com.example.ecobazaar.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // check duplicate email -> save new user -> return response
    public UserResponse register(RegisterRequest request){

        Optional<User> existing = userRepository.findByEmail(request.getEmail());
        if( existing.isPresent()){
            throw new RuntimeException("Email already taken");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole("CUSTOMER");
        user.setEcoScore(0);

        userRepository.save(user);

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getEcoScore()
        );


    }

    // fetch user by email -> compare password -> return response
    public UserResponse login (LoginRequest request){

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if ( !user.getPassword().equals(request.getPassword())){
            throw new RuntimeException("Invalid Password");
        }

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getEcoScore()
        );
    }

    // Login user

    public UserResponse login(LoginRequest login) {
        User user = userRepository.findByEmail(login.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found!"));

        if (!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials!");
        }


        String token = jwtUtil.generateToken(user.getEmail(), user.getRole(), user.getId());
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole(), user.getEcoScore(), token);

    }
}
