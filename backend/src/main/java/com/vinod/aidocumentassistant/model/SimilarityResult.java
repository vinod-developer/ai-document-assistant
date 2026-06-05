package com.vinod.aidocumentassistant.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SimilarityResult {

    private DocumentChunk chunk;

    private double score;
}