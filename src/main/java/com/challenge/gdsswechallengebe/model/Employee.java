package com.challenge.gdsswechallengebe.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @Column (name= "id", unique = true)
    private String id;
    
    @Column (name= "login", unique = true)
    private String login;
    
    @Column (name= "name")
    private String name;
    
    @Column (name= "salary")
    private Double salary;

    // Constructor
    public Employee() {

    }

    public Employee(String id, String login, String name, Double salary) {
        super();
        this.id = id;
        this.login = login;
        this.name = name;
        this.salary = salary;
    }
}
