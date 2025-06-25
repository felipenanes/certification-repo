package com.nnsgroup.certification.attempts.domain;

import com.nnsgroup.certification.documents.domain.Document;
import com.nnsgroup.certification.providers.domain.Provider;
import com.nnsgroup.certification.users.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "attempts")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attempt {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "attempt_date", nullable = false)
    private LocalDateTime attemptDate;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

    @Column(name = "result_percentage", nullable = false, precision = 5, scale = 2)
    private BigDecimal resultPercentage;

    @Column(name = "approval", nullable = false)
    private Boolean approval;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id")
    private Document document;
}