CREATE SCHEMA `cinema` ;

CREATE TABLE `cinema`.`movies` (
  `movies_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `duration` int NOT NULL,
  PRIMARY KEY (`movies_id`),
  UNIQUE KEY `movies_id_UNIQUE` (`movies_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `cinema`.`movies` (`movies_id`,`name`,`duration`) VALUES (1,'Милен Фармер',60);
INSERT INTO `cinema`.`movies` (`movies_id`,`name`,`duration`) VALUES (2,'Любовь и голуби',90);
INSERT INTO `cinema`.`movies` (`movies_id`,`name`,`duration`) VALUES (3,'Тихое место',120);
INSERT INTO `cinema`.`movies` (`movies_id`,`name`,`duration`) VALUES (4,'Титаник',120);

CREATE TABLE `cinema`.`show_time` (
  `show_time_id` int NOT NULL AUTO_INCREMENT,
  `date_start` date NOT NULL,
  `date_to` date NOT NULL,
  `time_start` time NOT NULL,
  `time_to` time NOT NULL,
  `price` decimal(12,2) NOT NULL,
  PRIMARY KEY (`show_time_id`),
  UNIQUE KEY `show_time_id_UNIQUE` (`show_time_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO `cinema`.`show_time` (`show_time_id`,`date_start`,`date_to`,`time_start`,`time_to`,`price`) VALUES (1,'2020-07-20','2021-07-20','08:00:00','15:00:00',100.00);
INSERT INTO `cinema`.`show_time` (`show_time_id`,`date_start`,`date_to`,`time_start`,`time_to`,`price`) VALUES (2,'2020-07-20','2020-07-20','08:00:00','14:59:59',200.00);
INSERT INTO `cinema`.`show_time` (`show_time_id`,`date_start`,`date_to`,`time_start`,`time_to`,`price`) VALUES (3,'2020-07-20','2020-07-20','08:00:00','15:00:00',500.00);
INSERT INTO `cinema`.`show_time` (`show_time_id`,`date_start`,`date_to`,`time_start`,`time_to`,`price`) VALUES (4,'2020-07-15','2020-07-30','08:00:00','15:00:00',700.00);
INSERT INTO `cinema`.`show_time` (`show_time_id`,`date_start`,`date_to`,`time_start`,`time_to`,`price`) VALUES (5,'2020-07-20','2020-07-20','15:00:00','21:00:00',400.00);

CREATE TABLE `cinema`.`list` (
  `list_id` int NOT NULL AUTO_INCREMENT,
  `movies_id` int NOT NULL,
  `show_time_id` int NOT NULL,
  `show_date` date NOT NULL,
  `show_time` time NOT NULL,
  PRIMARY KEY (`list_id`),
  UNIQUE KEY `list_id_UNIQUE` (`list_id`),
  KEY `movies_id_idx` (`movies_id`),
  KEY `show_time_id_idx` (`show_time_id`),
  CONSTRAINT `movies_id` FOREIGN KEY (`movies_id`) REFERENCES `movies` (`movies_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `show_time_id` FOREIGN KEY (`show_time_id`) REFERENCES `show_time` (`show_time_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `cinema`.`list` (`list_id`,`movies_id`,`show_time_id`,`show_date`,`show_time`) VALUES (1,1,1,'2021-07-20','14:00:00');
INSERT INTO `cinema`.`list` (`list_id`,`movies_id`,`show_time_id`,`show_date`,`show_time`) VALUES (2,2,2,'2021-07-20','15:30:00');
INSERT INTO `cinema`.`list` (`list_id`,`movies_id`,`show_time_id`,`show_date`,`show_time`) VALUES (3,3,3,'2021-07-20','16:30:00');
INSERT INTO `cinema`.`list` (`list_id`,`movies_id`,`show_time_id`,`show_date`,`show_time`) VALUES (4,4,4,'2021-07-20','19:50:00');
INSERT INTO `cinema`.`list` (`list_id`,`movies_id`,`show_time_id`,`show_date`,`show_time`) VALUES (5,2,5,'2021-07-20','20:00:00');

CREATE TABLE `cinema`.`tickets` (
  `tickets_id` int NOT NULL AUTO_INCREMENT,
  `list_id` int NOT NULL,
  PRIMARY KEY (`tickets_id`),
  UNIQUE KEY `tickets_id_UNIQUE` (`tickets_id`),
  KEY `list_id_idx` (`list_id`),
  CONSTRAINT `list_id` FOREIGN KEY (`list_id`) REFERENCES `list` (`list_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `cinema`.`tickets`(`tickets_id`,`list_id`) VALUES (1,1);
INSERT INTO `cinema`.`tickets`(`tickets_id`,`list_id`) VALUES (2,2);
INSERT INTO `cinema`.`tickets`(`tickets_id`,`list_id`) VALUES (6,2);
INSERT INTO `cinema`.`tickets`(`tickets_id`,`list_id`) VALUES (3,3);
INSERT INTO `cinema`.`tickets`(`tickets_id`,`list_id`) VALUES (4,4);
INSERT INTO `cinema`.`tickets`(`tickets_id`,`list_id`) VALUES (5,4);
INSERT INTO `cinema`.`tickets`(`tickets_id`,`list_id`) VALUES (7,5);
INSERT INTO `cinema`.`tickets`(`tickets_id`,`list_id`) VALUES (8,5);
INSERT INTO `cinema`.`tickets`(`tickets_id`,`list_id`) VALUES (9,5);
