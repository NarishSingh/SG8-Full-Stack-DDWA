package com.sg.M4L3classroster.dao;

import com.sg.M4L3classroster.model.Student;
import java.sql.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class StudentDAODb implements StudentDAO {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Student readStudentById(int id) {
        try {
            String readQuery = "SELECT * FROM student "
                    + "WHERE id = ?;";

            return jdbc.queryForObject(readQuery, new StudentMapper(), id);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Student> readAllStudents() {
        String readAllQuery = "SELECT * FROM student;";

        return jdbc.query(readAllQuery, new StudentMapper());
    }

    @Override
    @Transactional
    public Student createStudent(Student student) {
        String createQuery = "INSERT INTO student(firstName, lastName) "
                + "VALUES(?,?);";
        jdbc.update(createQuery,
                student.getFirstName(),
                student.getLastName());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID();", Integer.class);
        student.setId(newId);

        return student;
    }

    @Override
    public void updateStudent(Student student) {
        String updateQuery = "UPDATE student SET "
                + "firstName = ?, "
                + "lastName = ? "
                + "WHERE id = ?;";
        jdbc.update(updateQuery,
                student.getFirstName(),
                student.getLastName(),
                student.getId());
    }

    @Override
    @Transactional
    public void deleteStudentById(int id) {
        //delete from bridge
        String deleteCourseStudentQuery = "DELETE FROM course_student "
                + "WHERE studentId = ?;";
        jdbc.update(deleteCourseStudentQuery, id);

        //delete
        String deleteStudentQuery = "DELETE FROM student "
                + "WHERE id = ?;";
        jdbc.update(deleteStudentQuery, id);
    }

    public static final class StudentMapper implements RowMapper<Student> {

        @Override
        public Student mapRow(ResultSet rs, int index) throws SQLException {
            Student s = new Student();
            s.setId(rs.getInt("id"));
            s.setFirstName(rs.getString("firstName"));
            s.setLastName(rs.getString("lastName"));

            return s;
        }

    }

}
