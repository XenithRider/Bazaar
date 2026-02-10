package com.example.ecobazaar.service;

import com.example.ecobazaar.model.User;
import com.example.ecobazaar.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service  // This tells the spring : this is a service class
public class UserService {

    private final UserRepository userRepository ;


    // constructor injection
    public UserService( UserRepository userRepository){
        this.userRepository = userRepository ;
    }

    // Method to create new user
    public User createUser(User user){
        return userRepository.save(user) ; // saves into DB
    }

    // Method to get all Users
    public List<User> getAllUsers(){
        return userRepository.findAll(); // fetches from DB
    }




}
