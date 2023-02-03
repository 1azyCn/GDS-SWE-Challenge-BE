package com.challenge.gdsswechallengebe.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.gdsswechallengebe.dto.response.ResponseListEnvelope;
import com.challenge.gdsswechallengebe.model.Employee;
import com.challenge.gdsswechallengebe.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService{
    
    // Employee repo
    @Autowired
    EmployeeRepository employeeRepository;

    @SuppressWarnings("unchecked")
    @Override
    public <T> ResponseListEnvelope <T> getAllEmployeeRecords() {
        List<T> list = new ArrayList<T>();
        
        List<Employee> employeeList = employeeRepository.findAll();

        for (Employee employee : employeeList) {
            list.add((T) employee);
        }

        ResponseListEnvelope<T> responseListEnvelope = new ResponseListEnvelope<T>();
        responseListEnvelope.setList(list);
        responseListEnvelope.setTotalItems(employeeList.size());

        return responseListEnvelope;
    }
}
