package com.nnsgroup.certification.providers.repository;

import com.nnsgroup.certification.providers.domain.Provider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ProviderRepositoryTest {

    @Autowired
    private ProviderRepository providerRepository;

    Provider provider = Provider.builder()
            .name("Test Provider")
            .website("https://test.com")
            .build();

    @Test
    void testSaveAndFindById() {
        Provider saved = providerRepository.save(provider);
        
        assertNotNull(saved.getId());
        assertEquals("Test Provider", saved.getName());

        Optional<Provider> found = providerRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals(saved.getId(), found.get().getId());
        assertEquals("Test Provider", found.get().getName());
    }

    @Test
    void testFindById_NotFound() {
        UUID nonExistentId = UUID.randomUUID();
        Optional<Provider> found = providerRepository.findById(nonExistentId);
        assertFalse(found.isPresent());
    }

    @Test
    void testSave_WithId() {
        Provider saved = providerRepository.save(provider);
        
        assertEquals(provider.getId(), saved.getId());
        assertEquals("Test Provider", saved.getName());
    }
} 