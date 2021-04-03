CREATE TABLE category (
<<<<<<< HEAD
id bigserial PRIMARY KEY,
name VARCHAR(255),
created_at timestamp default current_timestamp,
update_at timestamp default current_timestamp,
deleted_at timestamp,
category_id bigserial
);
CREATE TABLE product (
id bigserial PRIMARY KEY,
=======
id serial not null constraint category_pk primary key,
name VARCHAR(255),
created_at timestamp default current_timestamp,
update_at timestamp default current_timestamp,
deleted_at timestamp
);
CREATE TABLE product (
id serial not null constraint product_pk primary key,
>>>>>>> Level6Lesson10
cost bigserial,
title VARCHAR(255),
created_at timestamp default current_timestamp,
update_at timestamp default current_timestamp,
deleted_at timestamp,
<<<<<<< HEAD
category_id bigserial
        REFERENCES category (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
=======
category_id serial
>>>>>>> Level6Lesson10
);
INSERT INTO category (name) VALUES
('Мясо'),
('Молочка');
INSERT INTO product (cost, title, category_id) VALUES
(10, 'Спички', 1),
(15, 'Масло', 1),
(20, 'Кефир', 1),
(25, 'Макароны', 1),
(30, 'Булочка', 1),
(35, 'Сосиски', 1),
(40, 'Торт', 1),
(45, 'Конфеты', 1),
(50, 'Чипсы', 1),
(55, 'Яйцо', 1),
(60, 'Хлеб', 1),
(65, 'Творог', 1),
(70, 'Мука', 1),
(75, 'Сахар', 2),
(80, 'Пельмени', 2),
(85, 'Пицца', 2),
(90, 'Молоко', 2),
(95, 'Сметана', 2),
(100, 'Мясо', 2),
<<<<<<< HEAD
(105, 'Сыр', 2);
=======
(105, 'Сыр', 2);

create table role(
id serial not null constraint role_pk primary key,
name varchar(20) not null
);

create table users(
id serial not null constraint user_pk primary key,
login varchar(50),
password varchar(500),
role_id integer constraint users_role_id_fk references role
);

create unique index users_login_uindex on users (login);

insert into role(name) values ('ROLE_ADMIN');
insert into role(name) values ('ROLE_USER');
>>>>>>> Level6Lesson10
