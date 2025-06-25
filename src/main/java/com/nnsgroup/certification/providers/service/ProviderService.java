package com.nnsgroup.certification.providers.service;

import com.nnsgroup.certification.providers.domain.Provider;

import java.util.UUID;

public interface ProviderService {

    Provider getById(UUID id);

    Provider create(Provider provider);
}