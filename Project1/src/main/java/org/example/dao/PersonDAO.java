package org.example.dao;


import org.example.models.Book;
import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDAO {

    private JdbcTemplate jdbcTemplate;
    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index()  {
        return jdbcTemplate.query("select * from Person", new PersonMapper());
    }

    public Person show(int id) {
        return jdbcTemplate.query("select * from Person left join book on person.personID = book.personID where person.personID = ?", new Object[]{id},
                new PersonMapper()).stream().findAny().orElse(null);
    }
    public List<Book> booksByPersonID(int id)  {
        return jdbcTemplate.query("select * from book where personid =?",
                new Object[]{id}, new BookMapper());
    }
    public void save(Person person) {
       jdbcTemplate.update("INSERT INTO Person(name, year_of_birth) VALUES( ?, ?)",
               person.getName(), person.getYear_of_birth());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE Person SET name=?, year_of_birth=? WHERE personID=?",
                updatedPerson.getName(), updatedPerson.getYear_of_birth(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE personID=?", id);
    }

}
