package com.sg.classroster.daos;

import com.sg.classroster.dtos.Course;
import com.sg.classroster.dtos.Student;
import com.sg.classroster.dtos.Teacher;
import java.util.List;

/**
 *
 * @author rhash
 */
public interface CourseDao {

    Course getCourseById(int id);

    List<Course> getAllCourses();

    Course addCourse(Course course);

    void updateCourse(Course course);

    void deleteCourseById(int id);

    /*helpers*/
    List<Course> getCoursesForTeacher(Teacher teacher);

    List<Course> getCoursesForStudent(Student student);
}
