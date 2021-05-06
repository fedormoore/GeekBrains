create table role(
id bigserial not null constraint role_pk primary key,
name varchar(20) not null
);

create table users(
id bigserial not null constraint user_pk primary key,
login varchar(50) not null,
password varchar(500) not null,
role_id bigint constraint users_role_id_fk references role
);

create unique index users_login_uindex on users (login);

insert into role(name) values ('ROLE_ADMIN');
insert into role(name) values ('ROLE_USER');