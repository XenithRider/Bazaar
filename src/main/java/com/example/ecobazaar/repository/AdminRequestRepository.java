package com.example.ecobazaar.repository;

import com.example.ecobazaar.model.AdminRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminRequestRepository extends JpaRepository<AdminRequest, Long> {

    List<AdminRequest> findByApprovedFalseAndRejectedFalseOrderByRequestedAtDesc();

    // FIXED: Changed from existsByUserIdAndApprovedFalseAndRejectedFalse
    boolean existsByUser_IdAndApprovedFalseAndRejectedFalse(Long userId);

    long countByApprovedFalseAndRejectedFalse();

    // FIXED: Changed from existsByUserId to existsByUser_Id
    boolean existsByUser_Id(Long userId);
}