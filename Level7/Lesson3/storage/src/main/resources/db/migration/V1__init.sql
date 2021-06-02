create table storage(
id serial not null constraint storage_pk primary key,
name varchar(255) not null,
location varchar(255) not null
);