package com.example.ecobazaar.controller;

import com.example.ecobazaar.dto.UserReport;
import com.example.ecobazaar.model.User;
import com.example.ecobazaar.repository.OrderRepository;
import com.example.ecobazaar.repository.UserRepository;
import com.example.ecobazaar.service.UserReportService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;

@RestController
@RequestMapping("/api/reports")
public class UserReportController {

    private final UserReportService userReportService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public UserReportController(UserReportService userReportService,
                                UserRepository userRepository,
                                OrderRepository orderRepository) {
        this.userReportService = userReportService;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public UserReport getUserReport() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userReportService.getUserReport(currentUser.getId());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/weekly")
    public List<Map<String, Object>> getWeeklyCarbon() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        LocalDate today = LocalDate.now();
        LocalDate start = today.minusDays(6);

        List<Object[]> savedRows = orderRepository.getDailyCarbonSaved(
                user.getId(), Date.valueOf(start), Date.valueOf(today));
        List<Object[]> usedRows = orderRepository.getDailyCarbonUsed(
                user.getId(), Date.valueOf(start), Date.valueOf(today));

        Map<LocalDate, Double> savedMap = new HashMap<>();
        Map<LocalDate, Double> usedMap = new HashMap<>();

        for (Object[] row : savedRows) {
            LocalDate d = toLocalDate(row[0]);
            if (d == null) continue;
            Double val = row[1] == null ? 0.0 : ((Number) row[1]).doubleValue();
            savedMap.put(d, val);
        }
        for (Object[] row : usedRows) {
            LocalDate d = toLocalDate(row[0]);
            if (d == null) continue;
            Double val = row[1] == null ? 0.0 : ((Number) row[1]).doubleValue();
            usedMap.put(d, val);
        }

        List<Map<String, Object>> result = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            LocalDate d = today.minusDays(i);
            Map<String, Object> day = new HashMap<>();
            day.put("day", d.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
            day.put("saved", savedMap.getOrDefault(d, 0.0));
            day.put("used",  usedMap.getOrDefault(d, 0.0));
            result.add(day);
        }
        return result;
    }

    /**
     * Safely converts whatever the JDBC driver returns for a DATE column into LocalDate.
     * Modern MySQL Connector/J (8.x) returns java.time.LocalDate directly.
     * Older drivers / some configs still return java.sql.Date.
     */
    private LocalDate toLocalDate(Object raw) {
        if (raw == null) return null;
        if (raw instanceof LocalDate ld) return ld;
        if (raw instanceof java.sql.Date sd) return sd.toLocalDate();
        // Fallback: try parsing the toString() representation (e.g. "2025-01-15")
        try {
            return LocalDate.parse(raw.toString().substring(0, 10));
        } catch (Exception e) {
            return null;
        }
    }
}