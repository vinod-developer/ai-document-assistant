package com.vinod.aidocumentassistant.service;


public interface AiService {

    String askQuestion(
            String documentContent,
            String question);
}
