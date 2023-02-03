package com.challenge.gdsswechallengebe.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.challenge.gdsswechallengebe.dto.response.FileUploadResponseDto;
import com.challenge.gdsswechallengebe.model.Employee;
import com.challenge.gdsswechallengebe.repository.EmployeeRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class FileUploadServiceImpl implements FileUploadService {

    // Employee repository
    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public boolean checkFileFormat(MultipartFile file) {
        final String Type = "text/csv";

        if (!Type.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    @Override
    public void uploadFile(InputStream is) {

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            CSVParser csvParser = new CSVParser(reader,
                    CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

            // List of Employee objects
            List<Employee> employeeRecords = new ArrayList<>();

            // String list : to check for duplicates
            List<String> duplicates = new ArrayList<>();

            // Iterate CSV records
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            // Loop through the records for validation checks
            for (CSVRecord csvRecord : csvRecords) {
                // log.info("What is this ->" + csvRecord);

                // Check if file is empty
                if (csvRecord.size() != 0) {
                    if (checkFourRecords(csvRecord)) {
                        // Place all records into a employee object
                        Employee employee = new Employee(
                                csvRecord.get("id"),
                                csvRecord.get("login"),
                                csvRecord.get("name"),
                                Double.parseDouble(csvRecord.get("salary")));
    
                        // log.info("Employee Records salary -> " + employee.getSalary());
    
                        if (checkforComments(employee))
                            continue;

                        // check if id or login has a duplicated value
                        if (duplicates.contains(employee.getId()) || duplicates.contains(employee.getLogin())) {
                            throw new IllegalArgumentException();
                        } else {
                            duplicates.add(employee.getId());
                            duplicates.add(employee.getLogin());
                        }    
    
                        if (checkSalaryFormat(employee) || checkSalaryDecimalPlace(employee)) {
                            throw new IllegalArgumentException();
                        }

                        // Check if reords already exits in DB
                        Optional <Employee> employeeExist = employeeRepository.findById(employee.getId());

                        // if similar employee id exists
                        if(employeeExist.isPresent()) {
                            Employee emp = employeeExist.get();

                            Optional <Employee> employeeLogin = employeeRepository.findByLogin(employee.getLogin()); 
                            
                            if (employeeLogin.isPresent()) {
                                throw new IllegalArgumentException();
                            } else {
                                emp.setName(employee.getName());
                                emp.setSalary(employee.getSalary());
                                emp.setLogin(employee.getLogin());

                                employeeRepository.save(emp);
                            }
                        } else {
                            employeeRecords.add(employee);
                        }
        
                        // log.info("What is this final employee ->" + employee.getId());
                    } else {
                        throw new IllegalArgumentException();
                    }
                    
                    // Closing the stream
                    is.close();
                    csvParser.close();
                    
                } else {
                    throw new IllegalArgumentException();
                }
            }
            employeeRepository.saveAll(employeeRecords);

        } catch (IOException ex) {
            throw new RuntimeException("Failed to parse CSV file: " + ex.getMessage());
        }
    }

    // Function to check if there are 4 records
    public static boolean checkFourRecords(CSVRecord records) {
        return (records.size() == 4);
    }

    // Function to check with first records of each row starts with #
    public static boolean checkforComments(Employee records) {
        return (records.getId().substring(0, 1).equals("#"));
    }

    // Function to check salary format
    public static boolean checkSalaryFormat(Employee records) {
        // Check if salary is >= 0.00
        return ((records.getSalary()) < 0.0);
    }

    // Function to check salary decimal place
    public static boolean checkSalaryDecimalPlace(Employee records) {
        // Check if records contains a decimal place
        String salaryStr = records.getSalary().toString();

        String[] salaryStrArr = salaryStr.split("\\.");

        return (salaryStrArr[1].length() > 1);
    }

}
