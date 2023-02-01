package com.challenge.gdsswechallengebe.dto.response;

public class FileUploadResponseDto {
    private String message;

    public FileUploadResponseDto (String message) {
        this.message = message;
      }
    
      public String getMessage() {
        return message;
      }
    
      public void setMessage(String message) {
        this.message = message;
      }
}
