package com.challenge.gdsswechallengebe.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

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
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));

            CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
            String[] records;

            while ((records = csvReader.readNext()) != null) {
                // check if there are only 4 column per records
                if (records.length < 4 || records.length > 4) {
                    throw new Exception("Too little or Too many column per record!");
                } else {
                    for (int i = 0; i < records.length; i++) {
                        // log.info("Record Details #" + i + " : " + records[i]);
                        // Check if first column of each record contains '#'
                        // Ignore whole records if true
                        if (!records[0].substring(0,1).equals("#")){
                            // log.info("Record Details(without first #) #" + i + " : " + records[i]);
                            // Check if salary are formatted correctly -> 2 decimal points
                            // Find the deciaml place of the salary
                            int integerplaces = records[3].indexOf(".");
                            int decimalplace = records[3].length() - integerplaces -1;
                            if (decimalplace != 2) {
                                log.info("Record Details(Correct formmated salary) #" + i + " : " + records[i]);
                                throw new Exception("Salary not formatted correctly");
                            }

                            // Check ifSalary >= 0.00
                            if (Double.parseDouble(records[3]) < 0.00) {
                                log.info("Record Details(Correct salary) #" + i + " : " + records[i]);
                                throw new Exception("Salary less than 0.00");
                            }
                        } 
                    }
                }
            }

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
