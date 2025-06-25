package com.nnsgroup.certification.attempts.service.impl;

import com.nnsgroup.certification.attempts.domain.Attempt;
import com.nnsgroup.certification.attempts.repository.AttemptRepository;
import com.nnsgroup.certification.attempts.service.AttemptService;
import com.nnsgroup.certification.attempts.web.dto.AttemptRequestDTO;
import com.nnsgroup.certification.documents.repository.DocumentRepository;
import com.nnsgroup.certification.providers.repository.ProviderRepository;
import com.nnsgroup.certification.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttemptServiceImpl implements AttemptService {

    private final AttemptRepository attemptRepository;
    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;
    private final ProviderRepository providerRepository;

    @Override
    public Attempt getById(UUID id) {
        return attemptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attempt not found"));
    }

    @Override
    public Attempt create(AttemptRequestDTO attemptRequestDTO) {
        Attempt attempt = Attempt.builder()
                .user(userRepository.getReferenceById(attemptRequestDTO.userId()))
                .provider(providerRepository.getReferenceById(attemptRequestDTO.provider()))
                .document(documentRepository.getReferenceById(attemptRequestDTO.document()))
                .resultPercentage(attemptRequestDTO.resultPercentage())
                .approval(attemptRequestDTO.approval())
                .attemptDate(attemptRequestDTO.attemptDate())
                .build();

        return attemptRepository.save(attempt);
    }
}