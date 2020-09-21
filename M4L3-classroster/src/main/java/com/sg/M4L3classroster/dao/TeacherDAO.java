package com.sg.M4L3classroster.dao;

import com.sg.M4L3classroster.model.Teacher;
import java.util.List;

public interface TeacherDAO {

    Teacher readTeacherById(int id);

    List<Teacher> readAllTeachers();

    Teacher createTeacher(Teacher teacher);

    void updateTeacher(Teacher teacher);

    void deleteTeacherById(int id);
}
