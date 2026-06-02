package com.vinod.aidocumentassistant.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    @PostMapping("/upload")
    public Map<String, String> upload(@RequestParam("file") MultipartFile file) {

        return Map.of(
                "message",
                file.getOriginalFilename() + " uploaded successfully");
    }



}
