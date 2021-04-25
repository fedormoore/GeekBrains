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

insert into role(name) values ('ROLE_ADMIN');
insert into role(name) values ('ROLE_USER');