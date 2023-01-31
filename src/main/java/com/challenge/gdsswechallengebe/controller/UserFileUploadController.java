package com.challenge.gdsswechallengebe.controller;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/users")
public class UserFileUploadController {
    

    // Upload & read from CSV file with following parameters (id, login, name, salary)
    // Request parameters = csv file
        // all parameters are non-nullable
        // id, login are unique
        // rows starting with # considered 'Comments' + ignored
    // Response = (SUCCESS : HTTP 200 OK, ERROR : )
   
    @RequestMapping("/upload")
    public String upload() {
        return "upload";
    }
}
