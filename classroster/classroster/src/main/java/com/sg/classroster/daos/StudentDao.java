package com.sg.classroster.daos;

import com.sg.classroster.dtos.Student;
import java.util.List;

/**
 *
 * @author rhash
 */
public interface StudentDao {

    Student getStudentById(int id);

    List<Student> getAllStudents();

    Student addStudent(Student student);

    void updateStudent(Student student);

    void deleteStudentById(int id);
}
