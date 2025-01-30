package org.example.Project2Boot.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;



import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Person")
public class Person {
    @Id
    @Column(name="personID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int personID;

    @NotEmpty(message="Name should not be empty")
    @Size(min=2,max = 30, message = "Name should be in 2 and 30 characters")
    @Column(name="name")
    private String name;

    @Min(value=1800, message = "Year should not be less than 1800")
    @Column(name="year_of_birth")
    private int yearOfBirth;

    @OneToMany(mappedBy = "owner")
    private List<Book>  books;

    public Person() {}

    public Person(int id, String name, int yearOfBirth, String email) {
        this.personID = id;
        this.name = name;
        this.yearOfBirth = yearOfBirth;
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int id) {
        this.personID = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
