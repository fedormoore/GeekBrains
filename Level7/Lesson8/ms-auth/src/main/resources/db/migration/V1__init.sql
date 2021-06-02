CREATE TABLE IF NOT EXISTS USERS (
id SERIAL CONSTRAINT users_id PRIMARY KEY,
login varchar(50),
email varchar(50),
password varchar(500),
active boolean,
activation_code varchar(255)
);