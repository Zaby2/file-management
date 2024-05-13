package com.docman.document.management.service;

import com.docman.document.management.entity.Document;
import com.docman.document.management.repository.DocumentRepository;
import lombok.NonNull;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class DocumentServiceImpl implements DocumentService {
    @Autowired
    DocumentRepository documentRepository;
    @Override
    @SneakyThrows
    public void saveDocument(MultipartFile multipartFile, String fileName, String author) {
        byte[] fileBinaryData = multipartFile.getBytes();
        Document document = new Document();
        document.setAuthor(author);
        document.setBinaryData(fileBinaryData);
        document.setDocumentName(fileName);
        document.setCreationDate(LocalDateTime.now());
        document.setUpdDate(LocalDateTime.now());
        document.setFileExtension(getFileExt(multipartFile.getOriginalFilename()));
        documentRepository.save(document);
    }

    @Override
    @SneakyThrows
    public File downloadFile(String id) {
        Document document = documentRepository.findById(Long.parseLong(id)).get();
        File file = new File(document.getDocumentName() + document.getFileExtension());
        OutputStream os = new FileOutputStream(file);
        os.write(document.getBinaryData());
        return file;

    }

    @Override
    public void editDocument(String id, String author, String fileName) {
        Optional<Document> document = documentRepository.findById(Long.parseLong(id));
        Document doc = document.get();
        doc.setDocumentName(fileName);
        doc.setAuthor(author);
        doc.setUpdDate(LocalDateTime.now());
        documentRepository.save(doc);
    }

    private String getFileExt(@NonNull String fileName) {
        int index = fileName.indexOf('.');
        return fileName.substring(index);
    }
}
