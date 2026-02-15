package com.example.ecobazaar.service;

import com.example.ecobazaar.dto.UserReport;
import com.example.ecobazaar.model.User;
import com.example.ecobazaar.repository.OrderRepository;
import com.example.ecobazaar.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserReportService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;


    // constructor injection
    public UserReportService(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    public UserReport getUserReport(Long userId) {
    // Fetch user data
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

    // Fetch and calculate order stats
        Long totalPurchases = (long) orderRepository.findByUserId(userId).size();
        Double totalSpent = orderRepository.getTotalSpentByUser(userId);
        Double totalCarbon = orderRepository.getTotalCarbonByUser(userId);

        if (totalSpent == null) totalSpent = 0.0;
        if (totalCarbon == null) totalCarbon = 0.0;
        String badge = getEcoBadge(totalCarbon);

        // Build and return report
        return new UserReport(
                user.getId(),
                user.getName(),
                totalPurchases,
                totalSpent,
                totalCarbon,
                badge
        );

    }

    private String getEcoBadge(Double carbonSaved) {
        if (carbonSaved > 500) return " Eco Legend";
        else if (carbonSaved > 200) return " Green Hero";
        else if (carbonSaved > 100) return " Conscious Shopper";
        else if (carbonSaved > 0) return " Beginner Eco-Saver";
        else return " No Impact Yet";

    }
}
