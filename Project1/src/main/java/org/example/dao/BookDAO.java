package org.example.dao;

import org.example.models.Book;
import org.example.models.Person;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    JdbcTemplate jdbcTemplate;

    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Book> index()  {
        return jdbcTemplate.query("select * from book", new BookMapper());
    }

    public Book show(int id) {
        return jdbcTemplate.query("select * from  book where bookID=?", new Object[]{id},
                new BookMapper()).stream().findAny().orElse(null);
    }
    public Optional<Person> getPersonByBookID(int id) {
        return jdbcTemplate.query("select * from person left join book on person.personid = book.personid where book.bookid=?",new Object[]{id},
                new PersonMapper()).stream().findAny();
    }
    public void releaseBook(int id) {
        jdbcTemplate.update("update book set personid = null where bookid=?", id);
    }
    public void assignBook(int id, Person person) {
        jdbcTemplate.update("update book set personid =? where book.bookid=?", person.getPersonID(), id);
    }
    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book(title, year, author) VALUES(?, ?, ?)",
                book.getTitle(), book.getYear(), book.getAuthor());
    }
    public void update(int id, Book updatedBook) {
        jdbcTemplate.update("UPDATE book SET title=?, year=?, author=? WHERE bookID=?",
                updatedBook.getTitle(), updatedBook.getYear(), updatedBook.getAuthor(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE bookID=?", id);
    }
}
