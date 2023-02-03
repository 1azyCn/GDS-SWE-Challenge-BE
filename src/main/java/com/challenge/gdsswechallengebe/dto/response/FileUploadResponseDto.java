package com.challenge.gdsswechallengebe.dto.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileUploadResponseDto {
    private String message;
    private HttpStatus statusCode;

    public FileUploadResponseDto (String message, HttpStatus statusCode) {
        this.message = message;
        this.statusCode = statusCode;
      }
}
