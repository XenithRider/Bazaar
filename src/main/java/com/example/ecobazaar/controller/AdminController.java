package com.example.ecobazaar.controller;

import com.example.ecobazaar.dto.PendingProductDto;
import com.example.ecobazaar.dto.PendingSellerDto;
import com.example.ecobazaar.model.Product;
import com.example.ecobazaar.model.User;
import com.example.ecobazaar.repository.ProductRepository;
import com.example.ecobazaar.repository.UserRepository;
import com.example.ecobazaar.service.AdminService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {

    private final AdminService adminService;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public AdminController(AdminService adminService,
                           ProductRepository productRepository,
                           UserRepository userRepository) {
        this.adminService = adminService;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @PutMapping("/approveProduct/{id}")
    public Product approveProduct(@PathVariable Long id) {
        return adminService.approveProduct(id);
    }

    @PutMapping("/approveSeller/{id}")
    public User approveSeller(@PathVariable Long id) {
        return adminService.approveSeller(id);
    }

    @PutMapping("/rejectSeller/{id}")
    public User rejectSeller(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setSellerRequestPending(false);
        return userRepository.save(user);
    }

    @GetMapping("/reports")
    public Map<String, Object> getReports() {
        return adminService.getAdminReport();
    }

    @GetMapping("/pending-products")
    public List<PendingProductDto> getPendingProducts() {
        return productRepository.findByEcoRequestedTrue().stream()
                .map(p -> new PendingProductDto(
                        p.getId(),
                        p.getName(),
                        p.getPrice(),
                        p.getCarbonImpact(),
                        p.getSeller() != null ? p.getSeller().getName() : "Unknown"
                ))
                .toList();
    }

    @GetMapping("/pending-sellers")
    public List<PendingSellerDto> getPendingSellers() {
        return userRepository.findBySellerRequestPendingTrue().stream()
                .map(u -> new PendingSellerDto(
                        u.getId(),
                        u.getName(),
                        u.getEmail(),
                        productRepository.findBySeller_Id(u.getId()).size()
                ))
                .toList();
    }

    @PutMapping("/rejectProduct/{id}")
    public Product rejectProduct(@PathVariable Long id) {
        return adminService.rejectProduct(id);
    }

    @GetMapping(value = "/reports/export", produces = "text/csv")
    public ResponseEntity<String> exportCsv() {
        String csv = adminService.generateReportCSV();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"orders-report.csv\"")
                .body(csv);
    }
}