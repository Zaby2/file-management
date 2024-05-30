package com.docman.document.management;

import com.docman.document.management.entity.Document;
import com.docman.document.management.repository.DocumentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class DocumentRepositoryTest {

    @Autowired
    DocumentRepository documentRepository;
    Document testDocument1;
    Document testDocument2;

    @BeforeEach
    public void setUp() {
        testDocument1 = Document
                .builder()
                .documentId(1L)
                .documentName("first")
                .build();
        testDocument2 = Document
                .builder()
                .documentId(2L)
                .documentName("second")
                .build();

    }
    @Test
    public void findByIdTest() {
        documentRepository.save(testDocument1);
        documentRepository.save(testDocument2);

        Document result = documentRepository.findById(1L).get();

        Assertions.assertNotNull(result);

        Assertions.assertEquals(testDocument1.getDocumentName(), result.getDocumentName());
    }
}
