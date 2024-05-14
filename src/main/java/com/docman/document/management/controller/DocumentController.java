package com.docman.document.management.controller;

import com.docman.document.management.entity.Document;
import com.docman.document.management.repository.DocumentRepository;
import com.docman.document.management.service.DocumentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1")
public class DocumentController {
    @Autowired
    DocumentRepository documentRepository;
    @Autowired
    DocumentService documentService;

    @GetMapping("/homePage")
    public ModelAndView homePage(HttpServletRequest request) {
        Iterable<Document> documents = documentRepository.findAll();
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("documents", documents);
        return mav;
    }
    @PostMapping("/upload")
    public String uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("fileName") String fileName,
            @RequestParam("author") String author, HttpServletResponse response) {

        documentService.saveDocument(file, fileName, author);
        try {
            response.sendRedirect("/v1/homePage");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/homePage";
    }
   @GetMapping(value = "/delete")
    public String deletion(@RequestParam String id, HttpServletResponse response) {
        documentRepository.delete(documentRepository.findById(Long.parseLong(id)).get());
       try {
           response.sendRedirect("/v1/homePage");
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
       return "redirect:v1";
    }

    @GetMapping("/download")
    public void downloadDocument(@RequestParam String id, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        File fileInput = documentService.downloadFile(id);
        Path file = Paths.get(fileInput.getAbsolutePath());
        if (Files.exists(file))
        {
            //response.setContentType("application/pdf");
            try {
                response.addHeader("Content-Disposition", "attachment; filename="+ URLEncoder.encode(String.valueOf(file.getFileName()), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            try
            {
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    @GetMapping("/edit")
    public ModelAndView editDocument(@RequestParam String id) {
        Optional<Document> documents = documentRepository.findById(Long.parseLong(id));
        ModelAndView mav = new ModelAndView("edit");
        mav.addObject("documents", documents.get());
        return mav;
    }
    @PostMapping("/edit")
    public void completeEdit( @RequestParam("fileName") String fileName,
                              @RequestParam("author") String author,
                              @RequestParam("id") String id,
                              HttpServletResponse response) {
        documentService.editDocument(id, author, fileName );
        try {
            response.sendRedirect("/v1/homePage");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/homePage")
    public ModelAndView searchForDocsWithExt(@RequestParam("ext") String extension, HttpServletResponse response) {
        if(extension.isEmpty())
        {
            try {
                response.sendRedirect("/v1/homePage");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        List<Document> documents =documentService.findByExt(extension);
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("documents", documents);
        return mav;
    }

}
