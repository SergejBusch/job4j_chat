package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.chat.domain.Role;
import ru.job4j.chat.repository.RoleRepository;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    RoleRepository repository;

    public RoleController(RoleRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public ResponseEntity<List<Role>> getAll() {
        return new ResponseEntity<>(
                repository.findAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getById(@PathVariable int id) {
        var role = repository.findById(id);
        return new ResponseEntity<>(
                repository.findById(id).orElse(new Role()),
                role.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping("/")
    public ResponseEntity<Role> create(@RequestBody Role role) {
        if (repository.findByName(role.getRoleName()).isEmpty()) {
            return new ResponseEntity<>(
                    repository.save(role),
                    HttpStatus.CREATED
            );
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Role role = new Role();
        role.setId(id);
        repository.delete(role);
        return ResponseEntity.ok().build();
    }
}
