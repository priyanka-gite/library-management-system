package com.novilms.librarymanagementsystem.fileUploadResponse;

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
