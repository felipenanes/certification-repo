package com.nnsgroup.certification.documents.service.impl;

import com.nnsgroup.certification.documents.domain.Document;
import com.nnsgroup.certification.documents.repository.DocumentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class DocumentServiceImplTest {

    @Mock
    private DocumentRepository documentRepository;

    @InjectMocks
    private DocumentServiceImpl documentService;

    private Document document;
    private UUID documentId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        documentId = UUID.randomUUID();
        document = Document.builder()
                .id(documentId)
                .name("AWS Certified Cloud Practitioner")
                .fileUrl("https://cp.certmetrics.com/amazon/en/public/verify/credential/7P8J7F7CVFBQQWG3")
                .build();
    }

    @Test
    void testGetById_Success() {
        when(documentRepository.findById(documentId)).thenReturn(Optional.of(document));
        Document found = documentService.getById(documentId);
        assertNotNull(found);
        assertEquals(documentId, found.getId());
        assertEquals("AWS Certified Cloud Practitioner", found.getName());
    }

    @Test
    void testGetById_NotFound() {
        when(documentRepository.findById(documentId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> documentService.getById(documentId));
        assertEquals(String.format("Document not found: %s", documentId), exception.getMessage());
    }

    @Test
    void testCreate() {
        when(documentRepository.save(document)).thenReturn(document);
        Document created = documentService.create(document);
        assertNotNull(created);
        assertEquals(document.getName(), created.getName());
    }
}