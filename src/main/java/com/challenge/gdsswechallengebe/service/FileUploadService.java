package com.challenge.gdsswechallengebe.service;

import java.io.InputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileUploadService {

    // Check file format
    public boolean checkFileFormat(MultipartFile file);
    
    // Upload Csv file
    public void uploadFile(InputStream is);
}
