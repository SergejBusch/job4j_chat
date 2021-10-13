package ru.job4j.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.chat.domain.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    @Query("SELECT DISTINCT p FROM Person p LEFT JOIN FETCH p.roles LEFT JOIN FETCH p.rooms")
    List<Person> findAll();

    @Query("SELECT DISTINCT p FROM Person p LEFT JOIN FETCH p.roles LEFT JOIN FETCH p.rooms WHERE p.id = :id")
    Optional<Person> findById(int id);

    @Query("SELECT DISTINCT p FROM Person p LEFT JOIN FETCH p.roles LEFT JOIN FETCH p.rooms WHERE p.name = :name")
    Optional<Person> findByName(String name);

}
