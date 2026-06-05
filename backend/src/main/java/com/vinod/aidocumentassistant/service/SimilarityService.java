package com.vinod.aidocumentassistant.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vinod.aidocumentassistant.model.DocumentChunk;
import com.vinod.aidocumentassistant.model.SimilarityResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SimilarityService {

    private final ObjectMapper objectMapper =
            new ObjectMapper();

    public List<DocumentChunk> findTopChunks(
            String questionEmbedding,
            List<DocumentChunk> chunks) {

        List<Double> questionVector =
                parseEmbedding(questionEmbedding);

        List<SimilarityResult> rankedChunks =
                chunks.stream()
                        .map(chunk -> {

                            List<Double> chunkVector =
                                    parseEmbedding(
                                            chunk.getEmbedding()
                                    );

                            double similarity =
                                    cosineSimilarity(
                                            questionVector,
                                            chunkVector
                                    );

                            return new SimilarityResult(
                                    chunk,
                                    similarity
                            );
                        })
                        .sorted(
                                Comparator.comparingDouble(
                                        SimilarityResult::getScore
                                ).reversed()
                        )
                        .limit(3)
                        .toList();

        log.info("========== TOP MATCHING CHUNKS ==========");

        for (SimilarityResult result : rankedChunks) {

            log.info(
                    "Score: {}",
                    result.getScore()
            );

            log.info(
                    "Chunk Preview: {}",
                    getPreview(
                            result.getChunk()
                                    .getChunkText()
                    )
            );

            log.info("--------------------------------");
        }

        return rankedChunks.stream()
                .map(SimilarityResult::getChunk)
                .toList();
    }

    private List<Double> parseEmbedding(
            String embedding) {

        try {

            return objectMapper.readValue(
                    embedding,
                    new TypeReference<List<Double>>() {}
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to parse embedding",
                    e
            );
        }
    }

    private double cosineSimilarity(
            List<Double> v1,
            List<Double> v2) {

        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;

        for (int i = 0; i < v1.size(); i++) {

            dotProduct +=
                    v1.get(i) * v2.get(i);

            norm1 +=
                    v1.get(i) * v1.get(i);

            norm2 +=
                    v2.get(i) * v2.get(i);
        }

        return dotProduct /
                (
                        Math.sqrt(norm1)
                                * Math.sqrt(norm2)
                );
    }

    private String getPreview(
            String text) {

        return text.substring(
                0,
                Math.min(
                        150,
                        text.length()
                )
        );
    }
}