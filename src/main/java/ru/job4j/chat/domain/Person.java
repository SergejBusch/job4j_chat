package ru.job4j.chat.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "person", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String name;

    @NotNull
    private String password;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "room_person",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id"))
    private Set<Room> rooms = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "person_role",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<Message> message = new ArrayList<>();

    public static Person of(String name, String password, Set<Room> rooms, Set<Role> roles) {
        var person = new Person();
        person.setName(name);
        person.setPassword(password);
        if (rooms != null) {
            person.getRooms().addAll(rooms);
        }
        if (roles != null) {
            person.getRoles().addAll(roles);
        }
        return person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        return id == person.id
                && Objects.equals(name, person.name)
                && Objects.equals(password, person.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", rooms=" + rooms +
                ", roles=" + roles +
                '}';
    }
}
