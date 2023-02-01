package com.challenge.gdsswechallengebe.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.exceptions.CsvValidationException;

@Service
public interface FileUploadService {

    // Check file format
    public boolean checkFileFormat(MultipartFile file);
    
    // Upload Csv file
    public void uploadFile(MultipartFile file);
}
