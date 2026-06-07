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

//    private final SimilarityService similarityService;

    private final DocumentChunkRepository chunkRepository;

    public String retrieveContext(
            String documentId,
            String question) {

        String questionEmbedding =
                embeddingService.generateEmbedding(
                        question
                );

       /* List<DocumentChunk> chunks =
                chunkRepository.findByDocumentId(
                        documentId
                );

        List<DocumentChunk> topChunks =
                similarityService.findTopChunks(
                        questionEmbedding,
                        chunks
                );*/


        List<DocumentChunk> topChunks =
                chunkRepository
                        .findTopRelevantChunks(
                                documentId,
                                questionEmbedding
                        );

        log.info(
                "pgvector returned {} chunks",
                topChunks.size()
        );

        StringBuilder context = new StringBuilder();

        for (DocumentChunk chunk : topChunks) {

            context.append(chunk.getChunkText());
            context.append("\n\n");
        }

        log.info("===== RETRIEVED CONTEXT FROM PGVECTOR =====");
        log.info("Number of chunks retrieved: {}", topChunks.size());

        for (int i = 0; i < topChunks.size(); i++) {

            log.info(
                    "Chunk {} Preview: {}",
                    i + 1,
                    topChunks.get(i)
                            .getChunkText()
                            .substring(
                                    0,
                                    Math.min(
                                            150,
                                            topChunks.get(i)
                                                    .getChunkText()
                                                    .length()
                                    )
                            )
            );
        }

        log.debug("Full Context:\n{}", context);

        return context.toString();
    }
}