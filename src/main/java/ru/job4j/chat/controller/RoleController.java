package ru.job4j.chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.domain.Role;
import ru.job4j.chat.repository.RoleRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/role")
public class RoleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class.getSimpleName());

    private RoleRepository repository;

    private final ObjectMapper objectMapper;

    public RoleController(RoleRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
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
                repository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Role not exist"
                        )),
                HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Role> create(@RequestBody Role role) {
        var name = role.getRoleName();
        if (name == null) {
            throw new NullPointerException("Rolename mustn't be empty");
        }
        if (repository.findByName(role.getRoleName()).isEmpty()) {
            if (role.getPersons() == null) {
                throw new IllegalArgumentException("Persons are empty, omg");
            }
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

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public void exceptionHandler(Exception e, HttpServletRequest req, HttpServletResponse res)
        throws IOException {

        res.setStatus(HttpStatus.BAD_REQUEST.value());
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        res.getWriter().write(objectMapper.writeValueAsString(Map.of(
                "message", e.getMessage(),
                "type", e.getClass()
        )));
        LOGGER.error(e.getLocalizedMessage());
    }
}
