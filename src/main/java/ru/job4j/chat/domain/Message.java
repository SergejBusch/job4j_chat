package ru.job4j.chat.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String msg;

    private Timestamp created;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Person person;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Room room;

    public static Message of(String msg, Person person, Room room) {
        var message = new Message();
        message.setMsg(msg);
        message.setPerson(person);
        message.setRoom(room);
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Message message = (Message) o;
        return id == message.id && person.equals(message.person) && room.equals(message.room);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, msg, created, person, room);
    }
}
