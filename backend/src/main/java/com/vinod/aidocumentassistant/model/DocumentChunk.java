package com.vinod.aidocumentassistant.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "document_chunks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentChunk {

    @Id
    private String id;

    private String documentId;

    @Column(columnDefinition = "TEXT")
    private String chunkText;

    @Column(columnDefinition = "TEXT")
    private String embedding;
}