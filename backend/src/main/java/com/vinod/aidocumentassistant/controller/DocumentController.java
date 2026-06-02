package com.vinod.aidocumentassistant.controller;

import com.vinod.aidocumentassistant.model.QuestionRequest;
import com.vinod.aidocumentassistant.model.QuestionResponse;
import com.vinod.aidocumentassistant.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;


    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> upload(@RequestParam("file") MultipartFile file) throws IOException {

        String extractedText =
                documentService.saveAndExtract(file);

        return ResponseEntity.ok(
                Map.of("content", extractedText));
    }

    @PostMapping("/question")
    public QuestionResponse askQuestion(@RequestBody QuestionRequest request) {

        return new QuestionResponse(
                "Received question: " + request.question()
        );

    }
}
