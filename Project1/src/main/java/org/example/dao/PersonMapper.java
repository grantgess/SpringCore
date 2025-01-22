package org.example.dao;


import org.example.models.Book;
import org.example.models.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet resultSet, int i) throws SQLException {
        Person person = new Person();
        person.setPersonID(resultSet.getInt("personID"));
        person.setName(resultSet.getString("name"));
        person.setYear_of_birth(resultSet.getInt("year_of_birth"));
        return person;
    }
}