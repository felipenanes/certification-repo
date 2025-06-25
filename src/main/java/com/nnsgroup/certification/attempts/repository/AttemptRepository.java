package com.nnsgroup.certification.attempts.repository;

import com.nnsgroup.certification.attempts.domain.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttemptRepository extends JpaRepository<Attempt, UUID> {
}
