package com.nnsgroup.certification.providers.service.impl;

import com.nnsgroup.certification.providers.domain.Provider;
import com.nnsgroup.certification.providers.repository.ProviderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProviderServiceImplTest {

    @Mock
    private ProviderRepository providerRepository;

    @InjectMocks
    private ProviderServiceImpl providerService;

    private Provider provider;
    private UUID providerId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        providerId = UUID.randomUUID();
        provider = Provider.builder()
                .id(providerId)
                .name("Test Provider")
                .website("https://test.com")
                .build();
    }

    @Test
    void testGetById_Success() {
        when(providerRepository.findById(providerId)).thenReturn(Optional.of(provider));
        Provider found = providerService.getById(providerId);
        assertNotNull(found);
        assertEquals(providerId, found.getId());
        assertEquals("Test Provider", found.getName());
    }

    @Test
    void testGetById_NotFound() {
        when(providerRepository.findById(providerId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> providerService.getById(providerId));
        assertEquals("Provider not found", exception.getMessage());
    }

    @Test
    void testCreate() {
        when(providerRepository.save(provider)).thenReturn(provider);
        Provider created = providerService.create(provider);
        assertNotNull(created);
        assertEquals(provider.getName(), created.getName());
    }
} 