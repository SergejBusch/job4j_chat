package ru.job4j.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.chat.domain.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query("SELECT DISTINCT r FROM Role r LEFT JOIN FETCH r.persons WHERE r.id = :id")
    Optional<Role> findById(int id);

    @Query("SELECT DISTINCT r FROM Role r LEFT JOIN FETCH r.persons WHERE r.roleName = :name")
    Optional<Role> findByName(String name);

    @Query("SELECT DISTINCT r FROM Role r LEFT JOIN FETCH r.persons")
    List<Role> findByAll(int id);
}
