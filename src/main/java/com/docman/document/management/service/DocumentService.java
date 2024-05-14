package com.docman.document.management.service;

import com.docman.document.management.entity.Document;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface DocumentService {
    public void saveDocument(MultipartFile multipartFile, String fileName, String author);
    public File downloadFile(String id);
    public void editDocument(String id, String author, String fileName);
    public List<Document> findByExt(String ext);
}
