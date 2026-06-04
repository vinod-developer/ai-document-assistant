package com.vinod.aidocumentassistant.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DocumentStoreService {

    private final Map<String, String> documents =
            new ConcurrentHashMap<>();

    public String save(String content) {

        String documentId =
                UUID.randomUUID().toString();

        documents.put(documentId, content);

        return documentId;
    }

    public String getDocumentContent(
            String documentId) {

        return documents.get(documentId);
    }



}
