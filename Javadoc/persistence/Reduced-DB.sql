-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Nov 22, 2019 at 06:47 PM
-- Server version: 8.0.13-4
-- PHP Version: 7.2.24-0ubuntu0.18.04.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: NOMAD
--

-- --------------------------------------------------------

--
-- Table structure for table Booking
--
-- DROP TABLE Booking;
CREATE TABLE IF NOT EXISTS Booking (
  bookingID int(11) NOT NULL,
  userName VARCHAR(45) NOT NULL,
  excursionEventID int(11) NOT NULL,
  status enum('Open','Confirmed','Refund','Cancelled') NOT NULL
);

-- --------------------------------------------------------

--
-- Table structure for table Excursion
--
-- DROP TABLE Excursion;
CREATE TABLE IF NOT EXISTS Excursion (
  excursionID int(11) NOT NULL,
  excursionName varchar(45)  NOT NULL,
  excursionDesc text
);

-- --------------------------------------------------------

--
-- Table structure for table ExcursionEvent
--
-- DROP TABLE ExcursionEvent;
CREATE TABLE IF NOT EXISTS ExcursionEvent (
  excursionEventID int(11) NOT NULL,
  date datetime NOT NULL,
  excursionID int(11) NOT NULL
);

-- --------------------------------------------------------

--
-- Table structure for table Organizer
--
-- DROP TABLE Organizer;
CREATE TABLE IF NOT EXISTS Organizer (
  userName varchar(45)  NOT NULL,
  userPassword varchar(45)  NOT NULL
);

-- --------------------------------------------------------
--
-- Table structure for table Student
--
-- DROP TABLE Student;
CREATE TABLE IF NOT EXISTS Student (
  userName varchar(45)  NOT NULL,
  userPassword varchar(45)  NOT NULL
);

--
-- Indexes for dumped tables
--

--
-- Indexes for table Organizer
--
ALTER TABLE Organizer
  ADD PRIMARY KEY (userName);

--
-- Indexes for table Student
--
ALTER TABLE Student
  ADD PRIMARY KEY (userName);

--
-- Indexes for table Excursion
--
ALTER TABLE Excursion
  ADD PRIMARY KEY (excursionID);

--
-- Indexes for table ExcursionEvent
--
ALTER TABLE ExcursionEvent
  ADD PRIMARY KEY (excursionEventID);

--
-- Indexes for table Booking
--
ALTER TABLE Booking
  ADD PRIMARY KEY (bookingID);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table Booking
--
ALTER TABLE Booking
  MODIFY bookingID int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table Excursion
--
ALTER TABLE Excursion
  MODIFY excursionID int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table ExcursionEvent
--
ALTER TABLE ExcursionEvent
  MODIFY excursionEventID int(11) NOT NULL AUTO_INCREMENT;


--
-- Constraints for dumped tables
--

--
-- Constraints for table Booking
--
ALTER TABLE Booking
  ADD CONSTRAINT Booking_Student FOREIGN KEY (userName) REFERENCES Student (userName),
  ADD CONSTRAINT Booking_Excursion FOREIGN KEY (excursionEventID) REFERENCES ExcursionEvent (excursionEventID);

--
-- Constraints for table ExcursionEvent
--
ALTER TABLE ExcursionEvent
  ADD CONSTRAINT ExcursionEvent_Excursion FOREIGN KEY (excursionID) REFERENCES Excursion (excursionID);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
