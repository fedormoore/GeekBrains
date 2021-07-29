create table products(
id bigserial primary key,
title varchar(255),
price int,
created_at timestamp default current_timestamp,
updated_at timestamp default current_timestamp
);

insert into products (title, price)
values ('Молоко', 82),
       ('Яйцо', 100),
       ('Колбаса', 480),
       ('Сосиски', 180),
       ('Хлеб', 32),
       ('Печенье', 180),
       ('Пряники', 100),
       ('Чай', 130),
       ('Сахар', 46),
       ('test', 500),
       ('Курица', 380),
       ('Котлеты', 300),
       ('Пицца', 700),
       ('Сок', 122),
       ('Мука', 46),
       ('Масло', 500),
       ('Сметана', 38);