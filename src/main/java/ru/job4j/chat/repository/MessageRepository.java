package ru.job4j.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.chat.domain.Message;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Query("SELECT m FROM Message m JOIN FETCH m.person JOIN FETCH m.room WHERE m.id = :id")
    Optional<Message> finById(int id);

    @Query("SELECT m FROM Message m JOIN FETCH m.person JOIN FETCH m.room")
    List<Message> findAll();

    @Query("SELECT m FROM Message m JOIN FETCH m.person JOIN FETCH m.room WHERE m.msg = :message")
    List<Message> finByMessage(String message);
}
