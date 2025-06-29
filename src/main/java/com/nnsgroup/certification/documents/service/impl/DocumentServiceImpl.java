package com.nnsgroup.certification.documents.service.impl;

import com.nnsgroup.certification.documents.domain.Document;
import com.nnsgroup.certification.documents.exception.DocumentNotFoundException;
import com.nnsgroup.certification.documents.repository.DocumentRepository;
import com.nnsgroup.certification.documents.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    @Override
    public Document getById(UUID id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new DocumentNotFoundException(id));
    }

    @Override
    public Document create(Document document) {
        return documentRepository.save(document);
    }
}