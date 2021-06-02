create table category (
id serial not null constraint category_pk primary key,
name varchar(255) not null
);

create unique index category_name_uindex on category (name);

insert into category(id, name) values (1, 'Продуты');
insert into category(id, name) values (2, 'Электроника');

create table product(
id serial not null constraint product_pk primary key,
cost bigint not null,
name varchar(20) not null,
description varchar(200) not null,
image varchar(200) not null,
categories_id serial constraint product_categories_id_fk references category
);

create unique index product_name_uindex on product (name);

insert into product(cost, name, description, image, categories_id) values (10, 'Молоко', 'Жирность 3,2', 'image', 1);
insert into product(cost, name, description, image, categories_id) values (20, 'Хлеб', 'Вес 1 кг', 'image', 1);
insert into product(cost, name, description, image, categories_id) values (10, 'Телефон', 'Оперативноая память 256 Гб', 'image', 2);
insert into product(cost, name, description, image, categories_id) values (20, 'Телевизор', 'Диагональ 52 дюйма', 'image', 2);