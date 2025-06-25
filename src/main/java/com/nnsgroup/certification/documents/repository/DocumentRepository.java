package com.nnsgroup.certification.documents.repository;

import com.nnsgroup.certification.documents.domain.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DocumentRepository extends JpaRepository<Document, UUID> {
}
