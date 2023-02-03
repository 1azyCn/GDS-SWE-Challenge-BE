package com.challenge.gdsswechallengebe.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.challenge.gdsswechallengebe.dto.response.CommonResponseBody;
import com.challenge.gdsswechallengebe.dto.response.FileUploadResponseDto;
import com.challenge.gdsswechallengebe.dto.response.ResponseListEnvelope;
import com.challenge.gdsswechallengebe.exception.FileUploadException;
import com.challenge.gdsswechallengebe.service.EmployeeService;
import com.challenge.gdsswechallengebe.service.FileUploadService;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserFileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private EmployeeService employeeService;

    // Upload & read from CSV file with following parameters (id, login, name,
    // salary)
    // Request parameters = csv file
    // Response = (SUCCESS : HTTP 200 OK, ERROR : )
    @Transactional
    @RequestMapping(method = RequestMethod.POST, path = "/upload")
    @ResponseBody
    public ResponseEntity<FileUploadResponseDto> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        HttpStatus statusCode = HttpStatus.BAD_REQUEST;

        if (fileUploadService.checkFileFormat(file)) {
            try {
                fileUploadService.uploadFile(file.getInputStream());

                message = "File uploaded successfully!";
                statusCode = HttpStatus.OK;
                return ResponseEntity.status(HttpStatus.OK).body(new FileUploadResponseDto(message, statusCode));

            } catch (IllegalArgumentException e) {
                message = "Failed Validation, File upload failed!";
                statusCode = HttpStatus.BAD_REQUEST;
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new FileUploadResponseDto(message, statusCode));
                
            } catch (Exception e) {

                message = "File upload failed!";
                statusCode = HttpStatus.BAD_REQUEST;
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new FileUploadResponseDto(message, statusCode));
            }
        }

        message = "File type does not match csv file format, please upload a csv file!";

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FileUploadResponseDto(message, statusCode));
    }

    @RequestMapping(method = RequestMethod.GET, path = "", produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public <T> ResponseEntity <Object> getAllEmployeeRecords() {
        CommonResponseBody jsonObj = new CommonResponseBody();

        try {
            ResponseListEnvelope<T> response = employeeService.getAllEmployeeRecords();

            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("list", response.getList());
            dataMap.put("totalItems", response.getTotalItems());

            jsonObj.setResult(CommonResponseBody.KEY_RESULT_SUCCESS);
            jsonObj.setData(dataMap);
        } catch (NullPointerException ex) {
            jsonObj.setResult(CommonResponseBody.KEY_RESULT_FAIL);
			jsonObj.setErrorCode("400");
			jsonObj.setErrorMessage(
					String.format(CommonResponseBody.ERR_MISSING_INVALID_FIELD, ex.getLocalizedMessage()));
        } catch (IllegalArgumentException ex) {
            jsonObj.setResult(CommonResponseBody.KEY_RESULT_FAIL);
			jsonObj.setErrorCode("400");
			jsonObj.setErrorMessage(
					String.format(CommonResponseBody.ERR_MISSING_INVALID_FIELD, ex.getLocalizedMessage()));
        }

		return new ResponseEntity<>(jsonObj.getMap(), HttpStatus.OK);
    }

}
