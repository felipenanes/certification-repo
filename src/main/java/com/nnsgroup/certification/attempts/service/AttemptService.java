package com.nnsgroup.certification.attempts.service;

import com.nnsgroup.certification.attempts.domain.Attempt;
import com.nnsgroup.certification.attempts.web.dto.AttemptRequestDTO;

import java.util.UUID;

public interface AttemptService {

    Attempt getById(UUID id);

    Attempt create(AttemptRequestDTO attempt);
}