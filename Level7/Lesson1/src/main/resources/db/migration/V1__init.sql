create table role(
id serial not null constraint role_pk primary key,
name varchar(20) not null
);

create table users(
id serial not null constraint user_pk primary key,
login varchar(50) not null,
password varchar(500) not null,
role_id serial constraint users_role_id_fk references role
);

create table cashback(
id serial not null constraint cashback_pk primary key,
date timestamp not null,
sum integer not null,
user_id serial constraint cashback_users_id_fk references users
);

create table category(
id serial not null constraint category_pk primary key,
name varchar(255) not null
);

create table product(
id serial not null constraint product_pk primary key,
cost bigint not null,
name varchar(20) not null,
description varchar(200) not null,
image varchar(200) not null,
categories_id serial constraint product_categories_id_fk references category
);

create table promo(
id serial not null constraint promo_pk primary key,
date_from timestamp not null,
date_to timestamp not null,
discount integer not null,
product_id serial constraint promo_product_id_fk references product
);

create table product_comment(
id serial not null constraint product_comment_pk primary key,
date timestamp not null,
text varchar(255) not null,
user_id serial constraint product_comment_users_id_fk references users,
product_id serial constraint product_comment_product_id_fk references product
);

create table product_item(
id serial not null constraint product_item_pk primary key,
amount bigint not null
);

create table storage(
id serial not null constraint storage_pk primary key,
name varchar(255) not null,
location varchar(255) not null
);

create table cart(
id serial not null constraint cart_pk primary key,
user_id serial constraint product_comment_users_id_fk references users,
product_id serial constraint product_comment_product_id_fk references product
);

create table order_status(
id serial not null constraint order_status_pk primary key,
date_status timestamp not null,
status varchar(255) not null,
cart_id serial constraint order_status_cart_id_fk references cart
);

insert into role(name) values ('ROLE_ADMIN');
insert into role(name) values ('ROLE_USER');