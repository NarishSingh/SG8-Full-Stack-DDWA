package com.sg.classroster.daos;

import com.sg.classroster.dtos.Teacher;
import java.util.List;

/**
 *
 * @author rhash
 */
public interface TeacherDao {

    Teacher getTeacherById(int id);

    List<Teacher> getAllTeachers();

    Teacher addTeacher(Teacher teacher);

    void updateTeacher(Teacher teacher);

    void deleteTeacherById(int id);
}
