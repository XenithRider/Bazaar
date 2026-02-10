package com.example.ecobazaar.controller;


import com.example.ecobazaar.dto.LoginRequest;
import com.example.ecobazaar.dto.RegisterRequest;
import com.example.ecobazaar.dto.UserResponse;
import com.example.ecobazaar.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService authService ;

    public AuthController(AuthService authService){
        this.authService= authService ;
    }

    public ResponseEntity<UserResponse> login (@Valid @RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }


    public ResponseEntity<UserResponse> register (@Valid @RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }
}
