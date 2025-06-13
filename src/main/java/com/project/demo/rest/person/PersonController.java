package com.project.demo.rest.person;

import com.project.demo.logic.entity.http.GlobalResponseHandler;
import com.project.demo.logic.entity.person.Person;
import com.project.demo.logic.entity.person.PersonRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/people")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping
    public List<?> getAllPersons() {
        return personRepository.findAll();
    }

    @GetMapping("/{personId}")
    public Optional<Person> getPerson(@PathVariable Long personId) {
        return personRepository.findById(personId);
    }

    @PostMapping
    public Person createPerson(@RequestBody Person person) {
        return personRepository.save(person);
    }

    @PatchMapping("/{personId}")
    public ResponseEntity<?> patchPerson(@PathVariable Long personId, @RequestBody Person person, HttpServletRequest request) {
        Optional<Person> personOptional = personRepository.findById(personId);
        personOptional.ifPresent(value -> value.setName(person.getName()));
        personOptional.ifPresent(value -> value.setLastname(person.getLastname()));
        Optional<Person> foundPerson = Optional.of(personRepository.save(person));

        return new GlobalResponseHandler().handleResponse("Person updated successfully",
                foundPerson, HttpStatus.OK, request);
    }

    @DeleteMapping("/{personId}")
    public ResponseEntity<?> deletePerson(@PathVariable Long personId) {
        personRepository.deleteById(personId);
        return new ResponseEntity<>("Person deleted successfully", HttpStatus.OK);
    }




}