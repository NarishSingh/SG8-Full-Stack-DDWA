package com.sg.M4L3classroster.dao;

import com.sg.M4L3classroster.model.Course;
import com.sg.M4L3classroster.model.Student;
import com.sg.M4L3classroster.model.Teacher;
import java.util.List;

public interface CourseDAO {

    Course readCourseById(int id);

    List<Course> readAllCourses();

    Course createCourse(Course course);

    void updateCourse(Course course);

    void deleteCourseById(int id);

    List<Course> readCoursesForTeacher(Teacher teacher);

    List<Course> readCoursesForStudent(Student student);
}
