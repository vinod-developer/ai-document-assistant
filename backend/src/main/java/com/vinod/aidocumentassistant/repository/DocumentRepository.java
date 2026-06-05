package com.vinod.aidocumentassistant.repository;

import com.vinod.aidocumentassistant.model.Document;
import com.vinod.aidocumentassistant.model.DocumentChunk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository
        extends JpaRepository<Document, String> {


}