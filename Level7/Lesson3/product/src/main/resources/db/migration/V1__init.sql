create table product(
id serial not null constraint product_pk primary key,
cost bigint not null,
name varchar(20) not null,
description varchar(200) not null,
image varchar(200) not null,
categories_id serial constraint product_categories_id_fk references category
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

create table category(
id serial not null constraint category_pk primary key,
name varchar(255) not null
);