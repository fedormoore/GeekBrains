create table role_table(
id bigserial not null constraint role_table_pk primary key,
name varchar(20) not null
);

create table user_table(
id bigserial not null constraint user_table_pk primary key,
login varchar(50),
password varchar(500),
role_id bigint constraint user_table_role_table_id_fk references role_table
);

create unique index user_table_login_uindex on user_table (login);

insert into role_table(name) values ('ROLE_ADMIN');
insert into role_table(name) values ('ROLE_USER');