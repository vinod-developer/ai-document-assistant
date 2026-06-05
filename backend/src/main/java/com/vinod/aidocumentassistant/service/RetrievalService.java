package com.vinod.aidocumentassistant.service;

import com.vinod.aidocumentassistant.model.DocumentChunk;
import com.vinod.aidocumentassistant.repository.DocumentChunkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RetrievalService {

    private final EmbeddingService embeddingService;

    private final SimilarityService similarityService;

    private final DocumentChunkRepository chunkRepository;

    public String retrieveContext(
            String documentId,
            String question) {

        String questionEmbedding =
                embeddingService.generateEmbedding(
                        question
                );

        List<DocumentChunk> chunks =
                chunkRepository.findByDocumentId(
                        documentId
                );

        List<DocumentChunk> topChunks =
                similarityService.findTopChunks(
                        questionEmbedding,
                        chunks
                );

        StringBuilder context =
                new StringBuilder();

        for (DocumentChunk chunk : topChunks) {

            context.append(
                    chunk.getChunkText()
            );

            context.append("\n\n");
        }
        log.info("===== RETRIEVED CONTEXT =====");
        log.info(context.toString());

        return context.toString();
    }
}