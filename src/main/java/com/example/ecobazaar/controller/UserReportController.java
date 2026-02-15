package com.example.ecobazaar.controller;


import com.example.ecobazaar.dto.UserReport;
import com.example.ecobazaar.service.UserReportService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
@CrossOrigin(origins = "*") // allow frontend  to access this backend
public class UserReportController {

    private final UserReportService userReportService;


    public UserReportController(UserReportService userReportService) {
        this.userReportService = userReportService;
    }


    @GetMapping("/user/{id}")
    public UserReport getUserReport(@PathVariable Long id) {
        return userReportService.getUserReport(id);
    }
}
