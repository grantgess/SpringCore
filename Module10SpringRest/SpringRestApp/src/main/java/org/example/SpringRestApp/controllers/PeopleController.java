package org.example.SpringRestApp.controllers;

import jakarta.validation.Valid;
import org.example.SpringRestApp.dto.PersonDTO;
import org.example.SpringRestApp.models.Person;
import org.example.SpringRestApp.services.PeopleService;
import org.example.SpringRestApp.util.PersonErrorResponse;
import org.example.SpringRestApp.util.PersonNotCreatedException;
import org.example.SpringRestApp.util.PersonNotFoundException;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;
    @Autowired
    public PeopleController(PeopleService peopleService, ModelMapper modelMapper) {
        this.peopleService = peopleService;
        this.modelMapper = modelMapper;
    }
    @GetMapping
    public List<PersonDTO> getAllPeople() {
        return peopleService.getAllPeople().stream().map(this::convertToPersonDTO).toList();
    }
    @GetMapping("/{id}")
    public PersonDTO getPeopleById(@PathVariable("id") int id) {
        return convertToPersonDTO(peopleService.getPeopleById(id));
    }
    @PostMapping
    public ResponseEntity<HttpStatus> createPeople(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder str = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                str.append(fieldError.getField()).append(" : ")
                        .append(fieldError.getDefaultMessage()).append(";");
            }
            throw new PersonNotCreatedException(str.toString());
        }
        peopleService.save(convertToPerson(personDTO));
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
    public Person convertToPerson(PersonDTO personDto) {
        return modelMapper.map(personDto, Person.class);
    }
    public PersonDTO convertToPersonDTO(Person person) {
        return modelMapper.map(person, PersonDTO.class);
    }

}
