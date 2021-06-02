BEGIN;

DROP TABLE IF EXISTS product CASCADE;
CREATE TABLE product (id bigserial PRIMARY KEY, title VARCHAR(255), cost bigserial);
INSERT INTO product (title, cost) VALUES
('Хлеб', 10),
('Молоко', 25),
('Колбаса', 100),
('Мука', 30),
('Яйцо', 60),
('Кефир', 20),
('Рис', 55);

COMMIT;