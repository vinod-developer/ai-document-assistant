package com.vinod.aidocumentassistant.service;

import com.vinod.aidocumentassistant.model.GeminiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
@Slf4j
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

        try {

            GeminiResponse response =
                    callGemini(prompt);

            if (response == null
                    || response.candidates() == null
                    || response.candidates().isEmpty()) {

                log.warn(
                        "Received empty response from Gemini"
                );

                return "No response received from AI service.";
            }

            return response
                    .candidates()
                    .get(0)
                    .content()
                    .parts()
                    .get(0)
                    .text();

        } catch (
                HttpServerErrorException.ServiceUnavailable ex) {

            log.error(
                    "Gemini service unavailable",
                    ex
            );

            return "The AI service is temporarily busy. Please try again in a few moments.";

        } catch (Exception ex) {

            log.error(
                    "Unexpected error while calling Gemini",
                    ex
            );

            return "An unexpected error occurred while generating the answer.";
        }
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