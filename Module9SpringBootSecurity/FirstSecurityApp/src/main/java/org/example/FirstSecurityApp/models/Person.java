package org.example.FirstSecurityApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message="Name should not be empty")
    @Size(min=2,max = 30, message = "Name should be in 2 and 30 characters")
    @Column(name="username")
    private String name;

    @Min(value=1800, message = "Year should not be less than 1800")
    @Column(name="year_of_birth")
    private int yearOfBirth;

    @Column(name = "password")
    private String password;

    public Person() {}

    public Person(String name, int yearOfBirth, String password) {
        this.name = name;
        this.yearOfBirth = yearOfBirth;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }
}
