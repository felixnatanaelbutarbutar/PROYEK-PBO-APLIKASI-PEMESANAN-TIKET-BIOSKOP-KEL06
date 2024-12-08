CREATE DATABASE tiketbioskop;

USE tiketbioskop;

CREATE TABLE Movies (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    schedule VARCHAR(50) NOT NULL,
    price DOUBLE NOT NULL
);

CREATE TABLE Bookings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    movie_id INT NOT NULL,
    ticket_count INT NOT NULL,
    total_price DOUBLE NOT NULL,
    FOREIGN KEY (movie_id) REFERENCES Movies(id)
);

CREATE TABLE Users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

INSERT INTO Movies (title, schedule, price) VALUES
('Avatar 2', '10:00 AM', 50.0),
('Spiderman: No Way Home', '1:00 PM', 40.0),
('The Batman', '4:00 PM', 45.0);

select * from users;	
