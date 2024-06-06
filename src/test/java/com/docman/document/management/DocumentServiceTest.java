package com.docman.document.management;

import com.docman.document.management.entity.Document;
import com.docman.document.management.repository.DocumentRepository;
import com.docman.document.management.service.DocumentServiceImpl;
import org.h2.command.dml.MergeUsing;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class DocumentServiceTest {
    @Mock
    DocumentRepository documentRepository;
    @InjectMocks
    DocumentServiceImpl documentService;
    Document testDocument1;
    Document testDocument2;
    @BeforeEach
    public void setUp() {
        testDocument1 = Document
                .builder()
                .documentId(1L)
                .documentName("first")
                .fileExtension(".csv")
                .build();
        testDocument2 = Document
                .builder()
                .documentId(2L)
                .documentName("second")
                .fileExtension(".xls")
                .build();

    }
    @Test
    public void findByExtTest() {
        String ext = "xls";
        Mockito.when(documentRepository.findAll()).thenReturn(List.of(testDocument1, testDocument2));


        List<Document> result = documentService.findByExt(ext);


        Assertions.assertEquals(testDocument2.getDocumentId(), result.get(0).getDocumentId());
        Assertions.assertEquals(testDocument2.getDocumentName(), result.get(0).getDocumentName());
    }

}
