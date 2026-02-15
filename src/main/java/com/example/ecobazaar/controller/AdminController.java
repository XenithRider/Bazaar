package com.example.ecobazaar.controller;

import com.example.ecobazaar.model.Product;
import com.example.ecobazaar.model.User;
import com.example.ecobazaar.service.AdminService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;


    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/approveProduct/{id}")
    public Product approveProduct(@PathVariable Long id) {
        return adminService.approveProduct(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("approveSeller/{id}")
    public User approveSeller(@PathVariable Long id) {
        return adminService.approveSeller(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public List<User> getAllUsers(){
        return adminService.getAllUsers();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/reports")
    public Map<String, Object> getReports(){
        return adminService.getAdminReport();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/reports/download")
    public String downloadReport() {
        return adminService.generateReportCSV();
    }
}
