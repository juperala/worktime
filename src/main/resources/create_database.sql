DROP DATABASE IF EXISTS WorkTime;
CREATE DATABASE WorkTime;
USE WorkTime;

CREATE TABLE Employee (
  employeeId INT NOT NULL AUTO_INCREMENT,
  firstName varchar(30) NOT NULL,
  lastName varchar(30) NOT NULL,
  contractType ENUM('HOURLY_CONTRACT','MONTHLY_CONTRACT') NOT NULL,
  department ENUM('PRODUCTION','OFFICE') NOT NULL,
  PRIMARY KEY  (employeeId)
);

CREATE TABLE Notification (
  notificationId INT NOT NULL AUTO_INCREMENT,
  department ENUM('PRODUCTION','OFFICE') NOT NULL,
  validFrom date NOT NULL,
  validTo date NOT NULL,
  message text NOT NULL,
  PRIMARY KEY  (notificationId)
);

CREATE TABLE LogEntry (
  logId INT NOT NULL AUTO_INCREMENT,
  employeeId INT NOT NULL,
  logIn datetime NULL,
  breakIn datetime NULL,
  breakOut datetime NULL,
  logOut datetime NULL,
  PRIMARY KEY  (logId),
  KEY `fk_log_entry` (employeeId),
  CONSTRAINT `fk_log_entry` FOREIGN KEY (employeeId) REFERENCES Employee (employeeId) ON DELETE CASCADE
);
