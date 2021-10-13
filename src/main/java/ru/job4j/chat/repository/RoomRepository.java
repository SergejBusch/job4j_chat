package ru.job4j.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.chat.domain.Room;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    @Query("SELECT r FROM Room r left JOIN FETCH r.persons left JOIN FETCH r.messages WHERE r.id = :id")
    Optional<Room> findById(int id);

    @Query("SELECT DISTINCT r FROM Room r LEFT JOIN FETCH r.persons LEFT JOIN FETCH r.messages WHERE r.name = :name")
    Optional<Room> findByName(String name);

    @Query("SELECT DISTINCT r FROM Room r LEFT JOIN FETCH r.persons LEFT JOIN FETCH r.messages")
    List<Room> findAll(String name);
}
