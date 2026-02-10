package com.example.ecobazaar.service;


import com.example.ecobazaar.dto.LoginRequest;
import com.example.ecobazaar.dto.RegisterRequest;
import com.example.ecobazaar.dto.UserResponse;
import com.example.ecobazaar.model.User;
import com.example.ecobazaar.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService( UserRepository userRepository){
        this.userRepository = userRepository ;
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
}
