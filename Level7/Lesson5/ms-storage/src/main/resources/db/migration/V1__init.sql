create table storage(
id bigserial not null constraint role_pk primary key,
name varchar(20) not null,
location varchar(200) not null
);

create unique index storage_name_uindex on storage (name);

insert into storage(name, location) values ('Склад №1', 'г. Тюмень, ул. Широтная');
insert into storage(name, location) values ('Склад №2', 'г. Тюмень, ул. Республики');