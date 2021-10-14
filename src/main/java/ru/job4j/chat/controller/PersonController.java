package ru.job4j.chat.controller;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.domain.Person;
import ru.job4j.chat.repository.PersonRepository;

import java.util.List;

@ComponentScan
@RestController
@RequestMapping("/users")
public class PersonController {

    private PersonRepository repository;
    private BCryptPasswordEncoder encoder;

    public PersonController(PersonRepository repository, BCryptPasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
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
                repository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not exist"
                )),
                HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Person> signUp(@RequestBody Person person) {
        var username = person.getName();
        var password = person.getPassword();
        if (username == null || password == null) {
            throw new NullPointerException("Username and password mustn't be empty");
        }
        person.setPassword(encoder.encode(person.getPassword()));
        repository.save(person);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
