package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.chat.domain.Room;
import ru.job4j.chat.repository.RoomRepository;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    RoomRepository repository;

    public RoomController(RoomRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public ResponseEntity<List<Room>> getAll() {
        return new ResponseEntity<>(
                repository.findAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getById(@PathVariable int id) {
        var room = repository.findById(id);
        return new ResponseEntity<>(
                repository.findById(id).orElse(new Room()),
                room.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping("/")
    public ResponseEntity<Room> create(@RequestBody Room room) {
        if (repository.findByName(room.getName()).isEmpty()) {
            return new ResponseEntity<>(
                    repository.save(room),
                    HttpStatus.CREATED
            );
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Room room = new Room();
        room.setId(id);
        repository.delete(room);
        return ResponseEntity.ok().build();
    }
}
