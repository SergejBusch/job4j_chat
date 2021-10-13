INSERT INTO person(name, password)
VALUES
       ('Bobbie', 'bbb'),
       ('Victor', 'vvv'),
       ('Michael', 'mmm');

INSERT INTO role(role_name)
VALUES
        ('admin'),
        ('user');

INSERT INTO room(name)
VALUES
    ('room1'),
    ('room2');

INSERT INTO messages(msg, person_id, room_id)
VALUES
    ('msg1', 1, 1),
    ('msg2', 2, 1),
    ('msg3', 3, 1),

    ('msg4', 1, 2),
    ('msg5', 2, 2),
    ('msg6', 3, 2);

INSERT INTO person_role(person_id, role_id)
VALUES
    (1, 2),
    (2, 2),
    (2, 1),
    (3, 1);

INSERT INTO room_person(room_id, person_id)
VALUES
    (1, 1),
    (1, 2),
    (2, 2),
    (2, 3);



