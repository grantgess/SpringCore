package org.example.SpringRestApp.controllers;

import jakarta.validation.Valid;
import org.example.SpringRestApp.models.Person;
import org.example.SpringRestApp.services.PeopleService;
import org.example.SpringRestApp.util.PersonErrorResponse;
import org.example.SpringRestApp.util.PersonNotCreatedException;
import org.example.SpringRestApp.util.PersonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;
    @Autowired
    public PeopleController( PeopleService peopleService) {
        this.peopleService = peopleService;
    }
    @GetMapping
    public List<Person> getAllPeople() {
        return peopleService.getAllPeople();
    }
    @GetMapping("/{id}")
    public Person getPeopleById(@PathVariable("id") int id) {
        return peopleService.getPeopleById(id);
    }
    @PostMapping
    public ResponseEntity<HttpStatus> createPeople(@RequestBody @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder str = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                str.append(fieldError.getField()).append(" : ")
                        .append(fieldError.getDefaultMessage()).append(";");
            }
            throw new PersonNotCreatedException(str.toString());
        }
        peopleService.save(person);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleExceptions(PersonNotFoundException e) {
        PersonErrorResponse errorResponse = new PersonErrorResponse("Person not found!",
                System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleExceptions(PersonNotCreatedException e) {
        PersonErrorResponse errorResponse = new PersonErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
