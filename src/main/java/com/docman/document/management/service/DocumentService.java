package com.docman.document.management.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface DocumentService {
    public void saveDocument(MultipartFile multipartFile, String fileName, String author);
    public File downloadFile(String id);
    public void editDocument(String id, String author, String fileName);
}
