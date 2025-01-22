package org.example.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Book {

    private int bookID;
    @NotEmpty(message = "Title should not be empty")
    @Size(min = 2, max = 50, message = "Title should be in 2 and 50 characters")
    private String title;
    @NotEmpty(message = "Author's name should not be empty")
    @Size(min = 2, max = 50, message = "Author's name be in 2 and 50 characters")
    private String author;
    @Min(value = 0, message = "The publication year must be greater than 0")
    private int year;

    public Book() {}

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
}
