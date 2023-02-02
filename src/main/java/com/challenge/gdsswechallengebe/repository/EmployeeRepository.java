package com.challenge.gdsswechallengebe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.challenge.gdsswechallengebe.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String>{
    
}
