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
    CommandLineRunner loadData(UserRepository userRepository, PasswordEncoder encoder) {
        return args->{
            if(userRepository.findByEmail("admin@ecobazzar.com").isEmpty()) {
                User admin = new User();
                admin.setName("Default_Admin");
                admin.setEmail("admin@ecobazzar.com");
                admin.setPassword(encoder.encode("Admin@123"));
                admin.setRole("ROLE_ADMIN");
                admin.setEcoScore(0);
                userRepository.save(admin);

                System.out.println("Admin Created Successfully: admin@ecobazzar.com/Admin@123");

            }else {
                System.out.println("Admin user already exists");
            }
        };
    }

}