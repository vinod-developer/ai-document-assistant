package com.vinod.aidocumentassistant.service;

import org.springframework.stereotype.Service;

@Service
public class DocumentStoreService {
    private String documentContent;


    public void save(String content) {
        this.documentContent = content;
    }

    public String getDocumentContent() {
        return documentContent;
    }
}
