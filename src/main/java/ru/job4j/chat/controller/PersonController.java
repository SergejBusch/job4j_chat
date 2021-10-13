package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.chat.domain.Person;
import ru.job4j.chat.repository.PersonRepository;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    PersonRepository repository;

    public PersonController(PersonRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public ResponseEntity<List<Person>> getAll() {
        return new ResponseEntity<>(
                repository.findAll(),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getById(@PathVariable int id) {
        var person = repository.findById(id);
        return new ResponseEntity<>(
                repository.findById(id).orElse(new Person()),
                person.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
}
