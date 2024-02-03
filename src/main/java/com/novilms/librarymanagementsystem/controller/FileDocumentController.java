package com.novilms.librarymanagementsystem.controller;

import com.novilms.librarymanagementsystem.fileUploadResponse.FileUploadResponse;
import com.novilms.librarymanagementsystem.model.FileDocument;
import com.novilms.librarymanagementsystem.service.DatabaseService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.*;


@RestController
public class FileDocumentController {
    private final DatabaseService databaseService;

    public FileDocumentController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @PostMapping("single/uploadDb")
    public FileUploadResponse singleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {

        FileDocument fileDocument = databaseService.uploadFileDocument(file);
        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFromDB/").path(Objects.requireNonNull(file.getOriginalFilename())).toUriString();

        String contentType = file.getContentType();

        return new FileUploadResponse(fileDocument.getFileName(), url, contentType);
    }

    //    get for single download
    @GetMapping("/downloadFromDB/{fileName}")
    ResponseEntity<byte[]> downLoadSingleFile(@PathVariable String fileName, HttpServletRequest request) {

        FileDocument document = databaseService.singleFileDownload(fileName, request);


        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + document.getFileName()).body(document.getDocFile());
    }

}
