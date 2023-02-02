package com.challenge.gdsswechallengebe.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.challenge.gdsswechallengebe.model.Employee;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Override
    public boolean checkFileFormat(MultipartFile file) {
        final String Type = "text/csv";

        if (!Type.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    @Override
    public void uploadFile(MultipartFile file) {
        
        try {
            BufferedReader reader =  new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));

            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

            // List of Employee objects
            List<Employee> employeeRecords = new ArrayList<Employee>();

            // Iterate CSV records
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            // Loop through the records for validation checks
            for (CSVRecord csvRecord : csvRecords) { 
                log.info("What is this ->" + csvRecord);

                // Place all records into a employee object
                Employee employee = new Employee(
                        csvRecord.get("id"), 
                        csvRecord.get("login"), 
                        csvRecord.get("name"), 
                        Double.parseDouble(csvRecord.get("salary"))
                        );

                log.info("Employee Records salary -> " + employee.getSalary());

                if (checkforComments(employee))
                    continue;

                if (checkSalaryFormat(employee)) {
                    throw new IllegalArgumentException();
                }

                if (checkSalaryDecimalPlace(employee)) {
                    throw new IllegalArgumentException();
                }

                log.info("What is this final employee ->" + employee.getId());

            }
        
        } catch (IOException ex) {
            throw new RuntimeException("Failed to parse CSV file: " + ex.getMessage());
        } 
    }

    // Function to check if there are 4 records
    public static boolean checkFourRecords(CSVRecord records){
        return(records.get(0) != null);
    }
    
    // Function to check with first records of each row starts with #
    public static boolean checkforComments(Employee records) {
        return(records.getId().substring(0, 1).equals("#"));
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

        String [] salaryStrArr = salaryStr.split("\\.");

        return(salaryStrArr[1].length() > 2);
    }

}
