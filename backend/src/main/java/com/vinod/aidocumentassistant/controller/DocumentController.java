package com.vinod.aidocumentassistant.controller;

import com.vinod.aidocumentassistant.model.QuestionRequest;
import com.vinod.aidocumentassistant.model.UploadResponse;
import com.vinod.aidocumentassistant.service.AiService;
import com.vinod.aidocumentassistant.service.DocumentService;
import com.vinod.aidocumentassistant.service.DocumentStoreService;
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

    private final DocumentStoreService documentStoreService;

    private final AiService aiService;



    @PostMapping("/upload")
    public ResponseEntity<UploadResponse> upload(@RequestParam("file") MultipartFile file) throws IOException {

        String content =
                documentService.saveAndExtract(file);

        documentStoreService.save(content);

        String documentId =
                documentStoreService.save(content);

        return ResponseEntity.ok(
                new UploadResponse(
                        documentId,
                        file.getOriginalFilename()
                                + " uploaded successfully"
                )
        );
    }


    @PostMapping("/question")
    public ResponseEntity<Map<String, String>> askQuestion(
            @RequestBody QuestionRequest request) {

        String documentContent =
                documentStoreService.getDocumentContent(
                        request.documentId()
                );
        if (documentContent == null) {

            return ResponseEntity.badRequest()
                    .body(
                            Map.of(
                                    "error",
                                    "Document not found"
                            )
                    );
        }

        String answer =
                aiService.askQuestion(
                        documentContent,
                        request.question()
                );

        return ResponseEntity.ok(
                Map.of(
                        "answer",
                        answer
                )
        );
    }
}
