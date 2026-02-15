package com.example.ecobazaar.controller;


import com.example.ecobazaar.dto.UserReport;
import com.example.ecobazaar.model.User;
import com.example.ecobazaar.repository.UserRepository;
import com.example.ecobazaar.service.UserReportService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
@CrossOrigin(origins = "*") // allow frontend  to access this backend
public class UserReportController {

    private final UserReportService userReportService;
    private final UserRepository userRepository;

    public UserReportController(UserReportService userReportService, UserRepository userRepository) {
        this.userReportService = userReportService;
        this.userRepository = userRepository;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")

    public UserReport getUserReport() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        // Call the report service using the authenticated user's ID
        return userReportService.getUserReport(currentUser.getId());

    }
}
