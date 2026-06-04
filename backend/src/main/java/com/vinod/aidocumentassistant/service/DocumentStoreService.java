package com.vinod.aidocumentassistant.service;

import com.vinod.aidocumentassistant.model.Document;
import com.vinod.aidocumentassistant.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class DocumentStoreService {

    private final DocumentRepository repository;

    public String save(
            String fileName,
            String content) {

        String documentId =
                UUID.randomUUID().toString();

        Document document =
                Document.builder()
                        .id(documentId)
                        .fileName(fileName)
                        .content(content)
                        .build();

        repository.save(document);

        return documentId;
    }

    public String getDocumentContent(
            String documentId) {

        return repository.findById(documentId)
                .map(Document::getContent)
                .orElse(null);
    }
}