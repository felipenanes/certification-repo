package com.nnsgroup.certification.attempts.web.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record AttemptRequestDTO(
        UUID userId,
        UUID provider,
        UUID document,
        BigDecimal resultPercentage,
        Boolean approval,
        LocalDateTime attemptDate
) {}
