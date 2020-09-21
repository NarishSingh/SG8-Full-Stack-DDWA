DROP DATABASE IF EXISTS classRosterTest;
CREATE DATABASE classRosterTest;

USE classRosterTest;

CREATE TABLE teacher(
    id INT PRIMARY KEY AUTO_INCREMENT,
    firstName VARCHAR(30) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    specialty VARCHAR(50)
);

CREATE TABLE student(
    id INT PRIMARY KEY AUTO_INCREMENT,
    firstName VARCHAR(30) NOT NULL,
    lastName VARCHAR(50) NOT NULL
);

CREATE TABLE course(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    teacherId INT NOT NULL,
    CONSTRAINT fk_course_teacher FOREIGN KEY (teacherId) 
        REFERENCES teacher(id)
);

CREATE TABLE course_student(
    courseId INT NOT NULL,
    studentId INT NOT NULL,
    PRIMARY KEY(courseId, studentId),
    CONSTRAINT fk_courseId_course FOREIGN KEY (courseId) 
        REFERENCES course(id),
    CONSTRAINT fk_studentId_student FOREIGN KEY (studentId) 
        REFERENCES student(id)
);
