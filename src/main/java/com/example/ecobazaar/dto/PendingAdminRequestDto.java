package com.example.ecobazaar.dto;

import java.time.LocalDateTime;

public record PendingAdminRequestDto(
        Long id,
        Long userId,
        String userName,
        String userEmail,
        LocalDateTime requestedAt
) {}
