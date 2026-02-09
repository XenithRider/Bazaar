package com.example.ecobazaar.repository;

import com.example.ecobazaar.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
