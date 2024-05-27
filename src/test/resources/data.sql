INSERT INTO room
    (room_type, seat_number)
VALUES (0, 1);
INSERT INTO room
    (room_type, seat_number)
VALUES (0, 2);
INSERT INTO room
    (room_type, seat_number)
VALUES (0, 3);
INSERT INTO room
    (room_type, seat_number)
VALUES (0, 4);
INSERT INTO room
    (room_type, seat_number)
VALUES (0, 5);
INSERT INTO room
    (room_type, seat_number)
VALUES (0, 6);

INSERT INTO room
    (room_type, seat_number)
VALUES (1, 1);
INSERT INTO room
    (room_type, seat_number)
VALUES (1, 2);
INSERT INTO room
    (room_type, seat_number)
VALUES (1, 3);
INSERT INTO room
    (room_type, seat_number)
VALUES (1, 4);
INSERT INTO room
    (room_type, seat_number)
VALUES (1, 5);

INSERT INTO room
    (room_type, seat_number)
VALUES (2, 1);
INSERT INTO room
    (room_type, seat_number)
VALUES (2, 2);
INSERT INTO room
    (room_type, seat_number)
VALUES (2, 3);
INSERT INTO room
    (room_type, seat_number)
VALUES (2, 4);
INSERT INTO room
    (room_type, seat_number)
VALUES (2, 5);
INSERT INTO room
    (room_type, seat_number)
VALUES (2, 6);
INSERT INTO room
    (room_type, seat_number)
VALUES (2, 7);
INSERT INTO room
    (room_type, seat_number)
VALUES (2, 8);

INSERT INTO users
    (name, phone_number, password, permission)
Values ('박원엽', '01099716733', '$2a$10$Zr.eYDhU2EWwlEdM1JRYCeeQeLIrGRZ2khYPJT0N0pbQiNhMRY9C2', 'ADMIN');

INSERT INTO users
    (name, phone_number, password, permission)
Values ('박원엽', '01099716737', '$2a$10$zsMnH.0WpF6y6oCKkH9OB.PHWrB0Tgb7t20hwJ8azvX19XbVXGxNG', 'ADMIN');

INSERT INTO user_token
    (access_token, refresh_token, user_id)
VALUES ('eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIwMTA5OTcxNjczMyIsImlhdCI6MTcxNjcyMjQ1MywiZXhwIjo0ODcyMzk2MDUzfQ.TpYFhStRMtDXP750Psdwm0r5sUuIxcFUg6DbxgtAnTzxG31CzP_1dlRFVeU3SQhW',
        'eyJhbGciOiJIUzM4NCJ9.eyJpYXQiOjE3MTY3MjI0NTMsImV4cCI6NDg3MjM5NjA1M30.97UZ03ZFcWFZ4B9upvsthB7awm4Xs7VkXYamG1KN6KW14_f4wuX2mKESJTpENZ8m',
        1);

INSERT INTO user_token
    (access_token, refresh_token, user_id)
VALUES ('eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIwMTA5OTcxNjczNyIsImlhdCI6MTcxNjcxOTI1NiwiZXhwIjo0ODcyMzkyODU2fQ.ScGLC0K8YBf_gOBTukV2ak0-Tv69k77s-biiBg1zGkJifY2cFuTKdYu9QAnsfYMq',
        'eyJhbGciOiJIUzM4NCJ9.eyJpYXQiOjE3MTY3MTkyNTYsImV4cCI6NDg3MjM5Mjg1Nn0.ZVmfA_STR-6KOITeNnrHXH8yuzdLSm4nH-OBYVhWX-Ov38VZnaDPM0Lxv3INBoz9',
        2);


INSERT INTO reservation
    (room_id, user_id, start_time, end_time)
VALUES (1, 1, '2024-06-04 00:00:00', '2024-06-04 01:00:00');
