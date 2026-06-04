package com.vinod.aidocumentassistant.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Document {

    @Id
    private String id;

    private String fileName;

    @Column(columnDefinition = "TEXT")
    private String content;
}