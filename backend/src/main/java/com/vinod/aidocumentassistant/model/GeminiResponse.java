package com.vinod.aidocumentassistant.model;

import java.util.List;

public record GeminiResponse(
        List<Candidate> candidates
) {
}
