package com.nnsgroup.certification.attempts.web.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record AttemptResponseDTO(
        UUID userId,
        String provider,
        String document,
        BigDecimal resultPercentage,
        Boolean approval,
        LocalDateTime attemptDate
) {}
