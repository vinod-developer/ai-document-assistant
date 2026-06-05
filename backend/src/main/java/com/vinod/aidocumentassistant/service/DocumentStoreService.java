package com.vinod.aidocumentassistant.service;

import com.vinod.aidocumentassistant.model.Document;
import com.vinod.aidocumentassistant.model.DocumentChunk;
import com.vinod.aidocumentassistant.repository.DocumentChunkRepository;
import com.vinod.aidocumentassistant.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class DocumentStoreService {

    private final DocumentRepository repository;

    private final ChunkingService chunkingService;

    private final DocumentChunkRepository documentChunkRepository;

    private final EmbeddingService embeddingService;

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
        List<String> chunks =
                chunkingService.chunkText(
                        content,
                        1500
                );

        for (String chunk : chunks) {

            String embedding =
                    embeddingService.generateEmbedding(
                            chunk
                    );

            DocumentChunk documentChunk =
                    DocumentChunk.builder()
                            .id(UUID.randomUUID().toString())
                            .documentId(documentId)
                            .chunkText(chunk)
                            .embedding(embedding)
                            .build();

            documentChunkRepository.save(
                    documentChunk
            );
        }


        return documentId;
    }

    public String getDocumentContent(
            String documentId) {

        return repository.findById(documentId)
                .map(Document::getContent)
                .orElse(null);
    }
}