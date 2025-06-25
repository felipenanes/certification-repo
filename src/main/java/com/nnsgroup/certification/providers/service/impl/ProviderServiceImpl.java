package com.nnsgroup.certification.providers.service.impl;

import com.nnsgroup.certification.providers.domain.Provider;
import com.nnsgroup.certification.providers.repository.ProviderRepository;
import com.nnsgroup.certification.providers.service.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProviderServiceImpl implements ProviderService {

    private final ProviderRepository providerRepository;

    @Override
    public Provider getById(UUID id) {
        return providerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Provider not found"));
    }

    @Override
    public Provider create(Provider provider) {
        return providerRepository.save(provider);
    }
}