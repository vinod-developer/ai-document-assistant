package com.vinod.aidocumentassistant.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Service
public class GeminiEmbeddingService
        implements EmbeddingService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final RestClient restClient =
            RestClient.builder().build();

    private final ObjectMapper objectMapper =
            new ObjectMapper();

    @Override
    public String generateEmbedding(String text) {

        try {

            String requestBody = """
                {
                  "model": "models/gemini-embedding-001",
                  "content": {
                    "parts": [
                      {
                        "text": "%s"
                      }
                    ]
                  }
                }
                """.formatted(
                    text
                            .replace("\\", "\\\\")
                            .replace("\"", "\\\"")
                            .replace("\n", "\\n")
            );

            String response =
                    restClient.post()
                            .uri(
                                    "https://generativelanguage.googleapis.com/v1beta/models/gemini-embedding-001:embedContent?key="
                                            + apiKey
                            )
                            .header(
                                    HttpHeaders.CONTENT_TYPE,
                                    MediaType.APPLICATION_JSON_VALUE
                            )
                            .body(requestBody)
                            .retrieve()
                            .body(String.class);

            JsonNode root =
                    objectMapper.readTree(response);

            JsonNode values =
                    root.path("embedding")
                            .path("values");

            return objectMapper.writeValueAsString(values);

        } catch (HttpClientErrorException.TooManyRequests ex) {

            throw new RuntimeException(
                    "Gemini API quota exceeded. Try again later."
            );


        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to generate embedding",
                    e
            );
        }
    }
}