package com.challenge.gdsswechallengebe.service;
import org.springframework.stereotype.Service;

import com.challenge.gdsswechallengebe.dto.response.ResponseListEnvelope;

@Service
public interface EmployeeService {
    
    // Get all Employee
    <T> ResponseListEnvelope <T> getAllEmployeeRecords(); 
}
