package com.sg.M4L3classroster.dao;

import com.sg.M4L3classroster.model.Student;
import java.util.List;

public interface StudentDAO {

    Student readStudentById(int id);

    List<Student> readAllStudents();

    Student createStudent(Student student);

    void updateStudent(Student student);

    void deleteStudentById(int id);
}
