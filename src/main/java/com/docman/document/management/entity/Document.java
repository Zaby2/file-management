package com.docman.document.management.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Document {
    @Id
    @GeneratedValue
    private Long documentId;
    @Column(nullable=false, length=512)
    private String documentName;
    @Column(nullable=false, length=512)
    private String author;
    @Column(nullable=false, length=512)
    private LocalDateTime creationDate;
    @Column(nullable=false, length=512)
    private LocalDateTime updDate;
    @Column(nullable=false, length=10000)
    private byte[] binaryData;
    private String fileExtension;

    public Document(Long documentId, String documentName, String author, LocalDateTime creationDate, byte[] binaryText,LocalDateTime updDate, String fileExtension) {
        super();
        this.documentId = documentId;
        this.documentName = documentName;
        this.author = author;
        this.creationDate = creationDate;
        this.binaryData = binaryText;
        this.updDate = updDate;
        this.fileExtension = fileExtension;
    }


}
