package com.novilms.librarymanagementsystem.FileUploadResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FileUploadResponse {
    String fileName;
    String contentType;
    String url;
}
