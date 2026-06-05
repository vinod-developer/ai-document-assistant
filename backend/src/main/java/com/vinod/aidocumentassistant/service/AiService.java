package com.vinod.aidocumentassistant.service;


public interface AiService {

    String askQuestion(
            String context,
            String question);
}
