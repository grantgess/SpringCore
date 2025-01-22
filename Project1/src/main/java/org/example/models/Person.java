package org.example.models;


import javax.validation.constraints.*;

public class Person {
    private int personID;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 50, message = "Name should be in 2 and 50 characters")
    private String name;

    @Min(value = 1900, message = "Age cannot be less than 1900")
    private int year_of_birth;


    public Person() {
    }

    public Person(int id, String name, int year_of_birth) {
        this.personID = id;
        this.name = name;
        this.year_of_birth = year_of_birth;
    }

    public int getYear_of_birth() {
        return year_of_birth;
    }

    public void setYear_of_birth( int year_of_birth) {
        this.year_of_birth = year_of_birth;
    }

    public String getName() {
        return name;
    }

    public void setName( String name) {
        this.name = name;
    }
    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int id) {
        this.personID = id;
    }
}
