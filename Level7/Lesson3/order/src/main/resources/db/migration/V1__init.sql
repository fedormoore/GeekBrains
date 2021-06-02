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