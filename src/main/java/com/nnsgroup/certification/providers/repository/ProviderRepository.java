package com.nnsgroup.certification.providers.repository;

import com.nnsgroup.certification.providers.domain.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProviderRepository extends JpaRepository<Provider, UUID> {
}
