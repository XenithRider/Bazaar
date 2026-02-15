package com.example.ecobazaar.config;


import com.example.ecobazaar.model.User;
import com.example.ecobazaar.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner loadData(UserRepository userRepo, PasswordEncoder encoder) {
        return args -> {
            // If no admin user exists, create one
            if (userRepo.findByEmail("admin@ecobazzar.com").isEmpty()) {
                User admin = new User();
                admin.setName("Admin");
                admin.setEmail("admin@ecobazzar.com");
                admin.setPassword(encoder.encode("Admin@123"));
                admin.setRole("ROLE_ADMIN"); // now a simple String
                admin.setEcoScore(0);
                userRepo.save(admin);
                System.out.println(" Admin user created: admin@ecobazzar.com / Admin@123");
            } else {
                System.out.println(" Admin user already exists.");
            }
        };
    }
}
