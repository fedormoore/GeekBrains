BEGIN;

DROP TABLE IF EXISTS order_product CASCADE;
DROP TABLE IF EXISTS product CASCADE;
DROP TABLE IF EXISTS orders CASCADE;
DROP TABLE IF EXISTS customer CASCADE;

CREATE TABLE customer (id bigserial PRIMARY KEY, name VARCHAR(255));
INSERT INTO customer (name) VALUES
('Вася'),
('Петя'),
('Маша'),
('Глаша');


CREATE TABLE product (id bigserial PRIMARY KEY, cost bigserial, title VARCHAR(255));
INSERT INTO product (cost, title) VALUES
(100, 'Молоко'),
(75, 'Сметана'),
(50, 'Кефир'),
(25, 'Сыр');


CREATE TABLE orders (id bigserial PRIMARY KEY, date timestamp without time zone, id_customer bigserial REFERENCES customer (id) MATCH SIMPLE ON UPDATE NO ACTION  ON DELETE NO ACTION);
INSERT INTO orders (id_customer, date) VALUES
(1, '2021-03-04 11:28:15'),
(1, '2021-03-05 11:28:15'),
(2, '2021-03-06 11:28:15'),
(3, '2021-03-07 11:28:15');


CREATE TABLE order_product (product_id bigserial, order_id bigserial,
        FOREIGN KEY (product_id)
        REFERENCES public.product (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
        FOREIGN KEY (order_id)
        REFERENCES public.orders (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION);
INSERT INTO order_product (product_id, order_id) VALUES
(1, 1),
(2, 1),
(3, 2),
(4, 3);

COMMIT;