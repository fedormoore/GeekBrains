create table order_status(
id bigserial not null constraint order_status_pk primary key,
name varchar(20) not null
);

create unique index order_status_name_uindex on order_status (name);

insert into order_status(name) values ('Новый');
insert into order_status(name) values ('Оплачен');
insert into order_status(name) values ('Выполнен');

create table order(
id bigserial not null constraint order_pk primary key,
order_status_id serial constraint order_status_id_fk references order_status
);