package com.sg.M4L3classroster.dao;

import com.sg.M4L3classroster.dao.StudentDAODb.StudentMapper;
import com.sg.M4L3classroster.dao.TeacherDAODb.TeacherMapper;
import com.sg.M4L3classroster.model.Course;
import com.sg.M4L3classroster.model.Student;
import com.sg.M4L3classroster.model.Teacher;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CourseDAODb implements CourseDAO {

    @Autowired
    JdbcTemplate jdbc;

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
        String readAllQuery = "SELECT * FROM course";

        List<Course> courses = jdbc.query(readAllQuery, new CourseMapper());
        associateTeacherAndStudents(courses);
        return courses;
    }

    @Override
    @Transactional
    public Course createCourse(Course course) {
        String updateQuery = "UPDATE course "
                + "SET name = ?, description = ?, teacherId = ? "
                + "WHERE id = ?;";
        
        jdbc.update(updateQuery, course.getName(),
                course.getDescription(),
                course.getTeacher().getId(),
                course.getId());
        
        //TODO CONTINUE HERE
    }

    @Override
    public void updateCourse(Course course) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteCourseById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

        return jdbc.queryForObject(readTeacherCourseQuery, new TeacherMapper(), id);
    }

    private List<Student> readStudentsForCourse(int id) {
        String readStudentCourseQuery = "SELECT s.* FROM student s "
                + "JOIN course_student cs ON cs.studentId = s.id "
                + "WHERE cs.courseId = ?;";

        return jdbc.query(readStudentCourseQuery, new StudentMapper(), id);
    }

    private void associateTeacherAndStudents(List<Course> courses) {
        for (Course c : courses) {
            c.setTeacher(readTeacherForCourse(c.getId()));
            c.setStudents(readStudentsForCourse(c.getId()));
        }
    }

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
