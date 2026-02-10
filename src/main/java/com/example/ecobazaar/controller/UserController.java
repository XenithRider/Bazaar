package com.example.ecobazaar.controller;


import com.example.ecobazaar.model.User;
import com.example.ecobazaar.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // makes this class handel the HTTP request
@RequestMapping("/users") // base URL : /users
public class UserController {

    private final UserService userService ;

    public UserController (UserService userService){
        this.userService = userService ;
    }


    // POST /users -> create user
    @PostMapping
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    // GET /users -> fetch all the user
    @GetMapping
    public List<User> getALlUsers(){
        return  userService.getAllUsers();
    }
}
