create table if not exists reservation
(
    id bigint auto_increment,
    user_id        bigint       not null,
    room_id        bigint       not null,
    start_time     timestamp(6) not null,
    end_time       timestamp(6) not null,
    create_at    timestamp(6),
    update_at    timestamp(6),
    delete_at    timestamp(6),
    primary key (id)
);

create table if not exists room
(
    id     bigint auto_increment,
    room_type   tinyint not null check (room_type between 0 and 2),
    seat_number integer not null,
    create_at    timestamp(6),
    update_at    timestamp(6),
    delete_at    timestamp(6),
    primary key (id)
);

create table if not exists user_token
(
    id      bigint auto_increment,
    user_id       bigint not null,
    access_token  varchar(255),
    refresh_token varchar(255),
    create_at    timestamp(6),
    update_at    timestamp(6),
    delete_at    timestamp(6),
    primary key (id)
);

create table if not exists users
(
    id      bigint auto_increment,
    name         varchar(255) not null,
    password     varchar(255) not null,
    phone_number varchar(255) not null,
    permission   ENUM ('USER','ADMIN'),
    create_at    timestamp(6),
    update_at    timestamp(6),
    delete_at    timestamp(6),
    primary key (id)
);

truncate table reservation restart identity;
truncate table room restart identity;
truncate table users restart identity;
truncate table user_token restart identity;

alter table if exists reservation
    add constraint FK26j1jgrx8n5n1xbc04y22tm6d
        foreign key (room_id)
            references room;

alter table if exists reservation
    add constraint FKrea93581tgkq61mdl13hehami
        foreign key (user_id)
            references users;

alter table if exists user_token
    add constraint FKfadr8tg4d19axmfe9fltvrmqt
        foreign key (user_id)
            references users;
