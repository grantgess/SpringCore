package org.example.repositories;

import org.example.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    List<Person> findByName(String Name);

    List<Person> findByNameOrderByAge(String Name);
    List<Person> findByEmail(String email);
    List<Person> findByNameStartingWith(String startingWith);
    List<Person> findByNameOrEmail(String name, String email);
}
