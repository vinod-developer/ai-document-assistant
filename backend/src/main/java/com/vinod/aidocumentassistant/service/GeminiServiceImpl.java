package com.vinod.aidocumentassistant.service;

import com.vinod.aidocumentassistant.model.GeminiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class GeminiServiceImpl implements AiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final RestClient restClient =
            RestClient.builder().build();

    @Override
    public String askQuestion(
            String documentContent,
            String question) {

        String prompt = buildPrompt(
                documentContent,
                question
        );

        GeminiResponse response =
                callGemini(prompt);

        return response
                .candidates()
                .get(0)
                .content()
                .parts()
                .get(0)
                .text();
    }

    private String buildPrompt(
            String context,
            String question) {

        return """
                You are a helpful document assistant.

                Answer the question using ONLY the information
                provided in the document below.

                If the answer is not present in the document,
                reply:
                "I could not find the answer in the document."

                DOCUMENT:
                %s

                QUESTION:
                %s
                """.formatted(
                context,
                question
        );
    }

    private GeminiResponse callGemini(
            String prompt) {

        String requestBody = """
                {
                  "contents": [
                    {
                      "parts": [
                        {
                          "text": "%s"
                        }
                      ]
                    }
                  ]
                }
                """.formatted(
                prompt
                        .replace("\\", "\\\\")
                        .replace("\"", "\\\"")
                        .replace("\n", "\\n")
        );

        return restClient.post()
                .uri(
                        "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key="
                                + apiKey
                )
                .header(
                        HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON_VALUE
                )
                .body(requestBody)
                .retrieve()
                .body(GeminiResponse.class);
    }
}