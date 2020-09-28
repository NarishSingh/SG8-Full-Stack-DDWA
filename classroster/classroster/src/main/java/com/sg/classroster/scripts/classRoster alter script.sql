/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  naris
 * Created: Sep 28, 2020
 */

USE classroster;

ALTER TABLE course
    ADD COLUMN photoFilename VARCHAR(255) AFTER teacherId;

ALTER TABLE student
    ADD COLUMN photoFilename VARCHAR(255) AFTER lastName;

ALTER TABLE teacher
    ADD COLUMN photoFilename VARCHAR(255) AFTER specialty;