/*

SQLyog Ultimate v8.55 
MySQL - 5.5.5-10.4.32-MariaDB : Database - tiketbioskop

*********************************************************************

*/



/*!40101 SET NAMES utf8 */;



/*!40101 SET SQL_MODE=''*/;



/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;

/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`tiketbioskop` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;



USE `tiketbioskop`;



/*Table structure for table `bookings` */



DROP TABLE IF EXISTS `bookings`;



CREATE TABLE `bookings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `booking_number` varchar(20) NOT NULL,
  `movie_id` int(11) NOT NULL,
  `ticket_count` int(11) NOT NULL,
  `total_price` double NOT NULL,
  `seat_numbers` varchar(255) NOT NULL,
  `booking_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `show_time` varchar(255) DEFAULT 'Not Specified',
  `available_seats` int(11) DEFAULT 50,
  PRIMARY KEY (`id`),
  UNIQUE KEY `booking_number` (`booking_number`),
  KEY `movie_id` (`movie_id`),
  CONSTRAINT `bookings_ibfk_1` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



/*Data for the table `bookings` */



LOCK TABLES `bookings` WRITE;



insert  into `bookings`(`id`,`booking_number`,`movie_id`,`ticket_count`,`total_price`,`seat_numbers`,`booking_date`,`show_time`,`available_seats`) values (1,'20241213-4FSW',14,12,480,'A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B1, B2','2024-12-13 21:09:53',NULL,50),(2,'20241213-SMLJ',15,49,1960,'A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, D1, D2, D3, D4, D5, D6, D7, D8, D9, D10, E1, E2, E3, E4, E5, E6, E7, E8, E9','2024-12-13 21:10:22',NULL,50),(3,'20241213-LZF5',15,1,40,'A1','2024-12-13 21:10:29',NULL,50);



UNLOCK TABLES;



/*Table structure for table `movies` */



DROP TABLE IF EXISTS `movies`;



CREATE TABLE `movies` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `schedule` varchar(50) NOT NULL,
  `price` double NOT NULL,
  `capacity` int(11) DEFAULT 50,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



/*Data for the table `movies` */



LOCK TABLES `movies` WRITE;



insert  into `movies`(`id`,`title`,`schedule`,`price`,`capacity`) values (1,'Avatar 3','10:00 AM',50,50),(2,'Spiderman: No Way Home','1:00 PM',40,50),(3,'The Batman','4:00 PM',45,50),(8,'Spiderman: No Way Home','1:00 PM',40,50),(10,'BOKEP','1:00 PM',40,50),(14,'BOKEP','1:00 PM',40,50),(15,'AAN','21:00 PM',40,50);



UNLOCK TABLES;



/*Table structure for table `seats` */



DROP TABLE IF EXISTS `seats`;



CREATE TABLE `seats` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `movie_id` int(11) NOT NULL,
  `seat_number` varchar(10) NOT NULL,
  `is_booked` tinyint(1) DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `movie_id` (`movie_id`),
  CONSTRAINT `seats_ibfk_1` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



/*Data for the table `seats` */



LOCK TABLES `seats` WRITE;



insert  into `seats`(`id`,`movie_id`,`seat_number`,`is_booked`) values (1,1,'A1',0),(2,1,'A2',0),(3,1,'A3',0),(4,1,'A4',0),(5,1,'A5',0),(6,1,'B6',0),(7,1,'B7',0),(8,1,'B8',0),(9,1,'B9',0),(10,1,'B10',0),(11,1,'B1',0),(12,1,'B2',0),(13,1,'B3',0),(14,1,'B4',0),(15,1,'B5',0),(16,1,'C6',0),(17,1,'C7',0),(18,1,'C8',0),(19,1,'C9',0),(20,1,'C10',0),(21,1,'C1',0),(22,1,'C2',0),(23,1,'C3',0),(24,1,'C4',0),(25,1,'C5',0),(26,1,'D6',0),(27,1,'D7',0),(28,1,'D8',0),(29,1,'D9',0),(30,1,'D10',0),(31,1,'D1',0),(32,1,'D2',0),(33,1,'D3',0),(34,1,'D4',0),(35,1,'D5',0),(36,1,'E6',0),(37,1,'E7',0),(38,1,'E8',0),(39,1,'E9',0),(40,1,'E10',0),(41,1,'E1',0),(42,1,'E2',0),(43,1,'E3',0),(44,1,'E4',0),(45,1,'E5',0),(46,1,'F6',0),(47,1,'F7',0),(48,1,'F8',0),(49,1,'F9',0),(50,1,'F10',0);



UNLOCK TABLES;



/*Table structure for table `users` */



DROP TABLE IF EXISTS `users`;



CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



/*Data for the table `users` */



LOCK TABLES `users` WRITE;



insert  into `users`(`id`,`username`,`password`,`email`) values (1,'felix','felix123','felix@gmail.com');



UNLOCK TABLES;



/* Procedure structure for procedure `InitializeSeats` */



/*!50003 DROP PROCEDURE IF EXISTS  `InitializeSeats` */;



DELIMITER $$



/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `InitializeSeats`(IN movieId INT, IN totalSeats INT)
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE rowLetter CHAR(1);
    DECLARE seatNumber INT;
    WHILE i <= totalSeats DO
        SET rowLetter = CHAR(65 + (i - 1) / 10); -- Baris A, B, C, dst.
        SET seatNumber = (i - 1) % 10 + 1;      -- Nomor kursi dalam baris
        INSERT INTO Seats (movie_id, seat_number, is_booked) 
        VALUES (movieId, CONCAT(rowLetter, seatNumber), FALSE);
        SET i = i + 1;
    END WHILE;
END */$$

DELIMITER ;



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;

/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;

/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

