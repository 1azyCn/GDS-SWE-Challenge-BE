package com.challenge.gdsswechallengebe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.challenge.gdsswechallengebe.dto.response.FileUploadResponseDto;
import com.challenge.gdsswechallengebe.service.FileUploadService;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserFileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    // Upload & read from CSV file with following parameters (id, login, name,
    // salary)
    // Request parameters = csv file
    // Response = (SUCCESS : HTTP 200 OK, ERROR : )
    @RequestMapping(method = RequestMethod.POST, path = "/upload")
    @ResponseBody
    public ResponseEntity<FileUploadResponseDto> uploadFile(MultipartFile file) {
        String message = "";

        if (fileUploadService.checkFileFormat(file)) {
            try {
                fileUploadService.uploadFile(file);

                message = "File uploaded successfully!" + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new FileUploadResponseDto(message));

            } catch (Exception e) {

                message = "File upload failed!" + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new FileUploadResponseDto(message));
            }
        }

        message = "File type does not match csv file format, please upload a csv file!";

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FileUploadResponseDto(message));
    }

}
