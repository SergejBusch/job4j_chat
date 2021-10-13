package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.chat.domain.Person;
import ru.job4j.chat.repository.PersonRepository;

@RestController
@RequestMapping("/register")
public class RegisterController {

    PersonRepository repository;

    public RegisterController(PersonRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/")
    public ResponseEntity<Person> register(@RequestBody Person person) {
        return new ResponseEntity<>(
                repository.save(person),
                HttpStatus.CREATED);
    }
}
