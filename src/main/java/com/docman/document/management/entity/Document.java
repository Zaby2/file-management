package com.docman.document.management.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Document {
    @Id
    @GeneratedValue
    private Long documentId;
    @Column( length=512)
    private String documentName;
    @Column( length=512)
    private String author;
    @Column( length=512)
    private LocalDateTime creationDate;
    @Column( length=512)
    private LocalDateTime updDate;
    @Column( length=10000)
    private byte[] binaryData;
    private String fileExtension;

}
