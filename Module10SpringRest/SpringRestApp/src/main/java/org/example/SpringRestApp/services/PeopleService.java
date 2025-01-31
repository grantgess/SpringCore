package org.example.SpringRestApp.services;

import org.example.SpringRestApp.models.Person;
import org.example.SpringRestApp.repositories.PeopleRepository;
import org.example.SpringRestApp.util.PersonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;
    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> getAllPeople() {
        return peopleRepository.findAll();
    }
    public Person getPeopleById(int id) {
        return peopleRepository.findById(id).orElseThrow(PersonNotFoundException::new);
    }
    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }
}
