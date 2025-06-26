package com.nnsgroup.certification.attempts.service.impl;

import com.nnsgroup.certification.attempts.domain.Attempt;
import com.nnsgroup.certification.attempts.mapper.AttemptMapper;
import com.nnsgroup.certification.attempts.repository.AttemptRepository;
import com.nnsgroup.certification.attempts.service.AttemptService;
import com.nnsgroup.certification.attempts.web.request.AttemptRequestDTO;
import com.nnsgroup.certification.documents.domain.Document;
import com.nnsgroup.certification.documents.repository.DocumentRepository;
import com.nnsgroup.certification.providers.domain.Provider;
import com.nnsgroup.certification.providers.repository.ProviderRepository;
import com.nnsgroup.certification.users.domain.User;
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
    private final AttemptMapper attemptMapper;

    @Override
    public Attempt getById(UUID id) {
        return attemptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attempt not found"));
    }

    @Override
    public Attempt create(AttemptRequestDTO dto) {
        User user = userRepository.getReferenceById(dto.userId());
        Provider provider = providerRepository.getReferenceById(dto.provider());
        Document document = dto.document() != null ? documentRepository.getReferenceById(dto.document()) : null;

        Attempt attempt = attemptMapper.toEntity(dto, user, provider, document);
        return attemptRepository.save(attempt);
    }
}