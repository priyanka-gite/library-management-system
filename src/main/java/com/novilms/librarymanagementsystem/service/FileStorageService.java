package com.novilms.librarymanagementsystem.service;

import com.novilms.librarymanagementsystem.model.FileDocument;
import com.novilms.librarymanagementsystem.repository.DocFileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class FileStorageService {
    private Path fileStoragePath;
    private final String fileStorageLocation;
    private final DocFileRepository docFileRepository;

    public FileStorageService(@Value("${lms.upload_location}") String fileStorageLocation, DocFileRepository docFileRepository) {
        fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();

        this.fileStorageLocation = fileStorageLocation;
        this.docFileRepository = docFileRepository;

        try {
            Files.createDirectories(fileStoragePath);
        } catch (IOException e) {
            throw new RuntimeException("File directory cannot be created.");
        }

    }

    public FileDocument uploadFileDocument(MultipartFile file, String url) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Path filePath = Paths.get(fileStoragePath + File.separator + fileName);
        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Problem during file storage", e);
        }
        FileDocument fileDocument = new FileDocument(null, fileName, file.getContentType(), url);
        docFileRepository.save(fileDocument);
        return fileDocument;
    }
}
