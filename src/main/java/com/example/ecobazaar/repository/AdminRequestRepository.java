package com.example.ecobazaar.repository;

import com.example.ecobazaar.model.AdminRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminRequestRepository extends JpaRepository<AdminRequest, Long> {
    List<AdminRequest> findByApprovedFalseAndRejectedFalseOrderByRequestedAtDesc();
    boolean existsByUserIdAndApprovedFalseAndRejectedFalse(Long userId);

    long countByApprovedFalseAndRejectedFalse();

    boolean existsByUserId(Long userId);
}
