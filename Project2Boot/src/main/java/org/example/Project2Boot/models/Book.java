package org.example.Project2Boot.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @Column(name = "bookID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookID;

    @NotEmpty(message = "Title should not be empty")
    @Size(min = 2, max = 50, message = "Title should be in 2 and 50 characters")
    @Column(name="title")
    private String title;

    @NotEmpty(message = "Author's name should not be empty")
    @Size(min = 2, max = 50, message = "Author's name be in 2 and 50 characters")
    @Column(name = "author")
    private String author;

    @Min(value = 0, message = "The publication year must be greater than 0")
    @Column(name="year")
    private int year;

    @ManyToOne
    @JoinColumn(name = "personID", referencedColumnName = "personID")
    private Person owner;
    public Book() {}

    @Column(name="book_taken_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bookTakenTime;

    @Transient
    private boolean delay;

    public Book(int id, String title, String author, int year) {
        this.bookID = id;
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }
    public Person getOwner() {
        return owner;
    }
    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Date getBookTakenTime() {
        return bookTakenTime;
    }

    public void setBookTakenTime(Date bookTakenTime) {
        this.bookTakenTime = bookTakenTime;
    }

    public boolean isDelay() {
        return delay;
    }

    public void setDelay(boolean delay) {
        this.delay = delay;
    }
}
