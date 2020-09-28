/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  naris
 * Created: Sep 28, 2020
 */

DROP DATABASE IF EXISTS classRoster;
CREATE DATABASE classRoster;
USE classRoster;
CREATE TABLE teacher(
    id INT PRIMARY KEY AUTO_INCREMENT,
    firstName VARCHAR(30) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    specialty VARCHAR(50),
    photoFilename VARCHAR(255)
);
CREATE TABLE student(
    id INT PRIMARY KEY AUTO_INCREMENT,
    firstName VARCHAR(30) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    photoFilename VARCHAR(255)
);
CREATE TABLE course(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    teacherId INT NOT NULL,
    FOREIGN KEY (teacherId) REFERENCES teacher(id),
    photoFilename VARCHAR(255)
);
CREATE TABLE course_student(
    courseId INT NOT NULL,
    studentId INT NOT NULL,
    PRIMARY KEY(courseId, studentId),
    FOREIGN KEY (courseId) REFERENCES course(id),
    FOREIGN KEY (studentId) REFERENCES student(id)
);