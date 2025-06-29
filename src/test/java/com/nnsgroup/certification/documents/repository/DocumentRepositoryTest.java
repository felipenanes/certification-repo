package com.nnsgroup.certification.documents.repository;

import com.nnsgroup.certification.documents.domain.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
class DocumentRepositoryTest {

    @Autowired
    private DocumentRepository documentRepository;

    Document document = Document.builder()
            .name("AWS Certified Cloud Practitioner")
            .fileUrl("https://cp.certmetrics.com/amazon/en/public/verify/credential/7P8J7F7CVFBQQWG3")
            .build();

    @Test
    void testSaveAndFindById() {
        Document saved = documentRepository.save(document);

        assertNotNull(saved.getId());
        assertEquals("AWS Certified Cloud Practitioner", saved.getName());

        Optional<Document> found = documentRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals(saved.getId(), found.get().getId());
        assertEquals("AWS Certified Cloud Practitioner", found.get().getName());
    }

    @Test
    void testFindById_NotFound() {
        UUID nonExistentId = UUID.randomUUID();
        Optional<Document> found = documentRepository.findById(nonExistentId);
        assertFalse(found.isPresent());
    }

    @Test
    void testSave_WithId() {
        Document saved = documentRepository.save(document);

        assertEquals(document.getId(), saved.getId());
        assertEquals("AWS Certified Cloud Practitioner", saved.getName());
    }
} 