package com.sg.M4L3classroster.dao;

import com.sg.M4L3classroster.dao.StudentDAODb.StudentMapper;
import com.sg.M4L3classroster.dao.TeacherDAODb.TeacherMapper;
import com.sg.M4L3classroster.model.Course;
import com.sg.M4L3classroster.model.Student;
import com.sg.M4L3classroster.model.Teacher;
import java.sql.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CourseDAODb implements CourseDAO {

    @Autowired
    JdbcTemplate jdbc;

    /*CRUD*/
    @Override
    public Course readCourseById(int id) {
        try {
            String readQuery = "SELECT * FROM course "
                    + "WHERE id = ?;";
            Course course = jdbc.queryForObject(readQuery, new CourseMapper(), id);
            course.setTeacher(readTeacherForCourse(id));
            course.setStudents(readStudentsForCourse(id));

            return course;
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Course> readAllCourses() {
        String readAllQuery = "SELECT * FROM course;";
        List<Course> courses = jdbc.query(readAllQuery, new CourseMapper());
        associateTeacherAndStudents(courses);
        
        return courses;
    }

    @Override
    @Transactional
    public Course createCourse(Course course) {
        String createQuery = "INSERT INTO course(name, description, teacherId) "
                + "VALUES(?,?,?);";
        jdbc.update(createQuery, 
                course.getName(),
                course.getDescription(),
                course.getTeacher().getId());
        
        //must insert before grabbing the id due to pk ai behavior
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID();", Integer.class);
        course.setId(newId);
        insertCourseStudent(course);
        
        return course;
    }

    @Override
    @Transactional
    public void updateCourse(Course course) {
        String updateQuery = "UPDATE course SET "
                + "name = ?, "
                + "description = ?, "
                + "teacherId = ? "
                + "WHERE id = ?;";
        jdbc.update(updateQuery, 
                course.getName(),
                course.getDescription(),
                course.getTeacher().getId(),
                course.getId());
        
        //delete and re-insert bridge table info
        //this also makes the update transactional
        String deleteQuery = "DELETE FROM course_student "
                + "WHERE courseId = ?;";
        jdbc.update(deleteQuery, course.getId());
        insertCourseStudent(course);
    }

    @Override
    @Transactional
    public void deleteCourseById(int id) {
        String deleteCourseStudentQuery = "DELETE FROM course_student "
                + "WHERE courseId = ?;";
        jdbc.update(deleteCourseStudentQuery, id);
        
        String deleteCourseQuery = "DELETE FROM course "
                + "WHERE id = ?;";
        jdbc.update(deleteCourseQuery, id);
    }

    @Override
    public List<Course> readCoursesForTeacher(Teacher teacher) {
        String readCoursesTeacherQuery = "SELECT * FROM course "
                + "WHERE teacherId = ?;";
        List<Course> courses = jdbc.query(readCoursesTeacherQuery, new CourseMapper(), teacher.getId());
        
        return courses;
    }

    @Override
    public List<Course> readCoursesForStudent(Student student) {
        String readCoursesStudentQuery = "SELECT c.* FROM course c "
                + "JOIN course_student cs ON cs.courseId = c.id "
                + "WHERE cs.studentId = ?;";
        List<Course> courses = jdbc.query(readCoursesStudentQuery, new CourseMapper(), student.getId());
        associateTeacherAndStudents(courses);

        return courses;
    }

    /*HELPERS*/
    private Teacher readTeacherForCourse(int id) {
        String readTeacherCourseQuery = "SELECT t.* FROM teacher t "
                + "JOIN course c ON c.teacherId = t.id "
                + "WHERE c.id = ?;";

        //no try-catch needed due to db constraint, course has an fk toa teacher
        return jdbc.queryForObject(readTeacherCourseQuery, new TeacherMapper(), id); 
    }

    private List<Student> readStudentsForCourse(int id) {
        String readStudentCourseQuery = "SELECT s.* FROM student s "
                + "JOIN course_student cs ON cs.studentId = s.id "
                + "WHERE cs.courseId = ?;";

        //no try-catched needed due to bridge table, must exist
        return jdbc.query(readStudentCourseQuery, new StudentMapper(), id);
    }

    private void associateTeacherAndStudents(List<Course> courses) {
        for (Course c : courses) {
            c.setTeacher(readTeacherForCourse(c.getId()));
            c.setStudents(readStudentsForCourse(c.getId()));
        }
    }

    private void insertCourseStudent(Course course) {
        String createCourseStudentQuery = "INSERT INTO course_student(courseId, studentId) "
                + "VALUES(?,?);";
        
        for (Student s : course.getStudents()) {
            jdbc.update(createCourseStudentQuery, 
                    course.getId(), 
                    s.getId());
        }
    }

    /**
     * Row Mapper Implementation for course model
     */
    public static class CourseMapper implements RowMapper<Course> {

        @Override
        public Course mapRow(ResultSet rs, int index) throws SQLException {
            Course c = new Course();
            c.setId(rs.getInt("id"));
            c.setName(rs.getString("name"));
            c.setDescription(rs.getString("description"));

            return c;
        }

    }

}
