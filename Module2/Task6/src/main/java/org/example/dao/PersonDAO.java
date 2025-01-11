package org.example.dao;

import org.example.models.Person;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    List<Person> people;
    private static int COUNTER;

    {
        people = new ArrayList<Person>();
        people.add(new Person("Tom", ++COUNTER));
        people.add(new Person("John", ++COUNTER));
        people.add(new Person("Bob", ++COUNTER));
        people.add(new Person("Mike", ++COUNTER));
    }
    public List<Person> index() {
        return people;
    }
    public Person show(int id) {
        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
    }
}