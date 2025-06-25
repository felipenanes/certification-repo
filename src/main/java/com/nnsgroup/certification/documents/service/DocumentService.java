package com.nnsgroup.certification.documents.service;

import com.nnsgroup.certification.documents.domain.Document;

import java.util.UUID;

public interface DocumentService {

    Document getById(UUID id);

    Document create(Document document);
}