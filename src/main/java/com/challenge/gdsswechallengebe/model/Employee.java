package com.challenge.gdsswechallengebe.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// @Entity
public class Employee {

    private String id;
    
    private String login;
    
    private String name;
    
    private Double salary;

    // Constructor
    public Employee(String id, String login, String name, Double salary) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.salary = salary;
    }
}
