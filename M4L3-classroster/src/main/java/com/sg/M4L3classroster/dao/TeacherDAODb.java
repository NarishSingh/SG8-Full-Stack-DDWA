package com.sg.M4L3classroster.dao;

import com.sg.M4L3classroster.model.Teacher;
import java.sql.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TeacherDAODb implements TeacherDAO {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Teacher readTeacherById(int id) {
        try {
            String readQuery = "SELECT * FROM teacher "
                    + "WHERE id = ?;";

            return jdbc.queryForObject(readQuery, new TeacherMapper(), id);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Teacher> readAllTeachers() {
        String readAllQuery = "SELECT * FROM teacher;";

        return jdbc.query(readAllQuery, new TeacherMapper());
    }

    @Override
    @Transactional
    public Teacher createTeacher(Teacher teacher) {
        String createQuery = "INSERT INTO teacher(firstName, lastName, specialty) "
                + "VALUES(?,?,?);";

        jdbc.update(createQuery,
                teacher.getFirstName(),
                teacher.getLastName(),
                teacher.getSpecialty());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID();", Integer.class);
        teacher.setId(newId);

        return teacher;
    }

    @Override
    public void updateTeacher(Teacher teacher) {
        String updateQuery = "UPDATE teacher SET "
                + "firstName = ?, "
                + "lastName = ?, "
                + "specialty = ? "
                + "WHERE id = ?;";

        jdbc.update(updateQuery,
                teacher.getFirstName(),
                teacher.getLastName(),
                teacher.getSpecialty(),
                teacher.getId());
    }

    @Override
    @Transactional
    public void deleteTeacherById(int id) {
        //delete from bridge table
        String bridgeDeleteQuery = "DELETE cs.* FROM course_student cs "
                + "JOIN course c ON cs.courseId = c.id "
                + "WHERE c.teacherId = ?;";
        jdbc.update(bridgeDeleteQuery, id);

        //delete from course
        String courseDeleteQuery = "DELETE FROM course "
                + "WHERE teacherId = ?;";
        jdbc.update(courseDeleteQuery, id);

        //delete from teacher
        String teacherDeleteQuery = "DELETE FROM teacher "
                + "WHERE id = ?;";
        jdbc.update(teacherDeleteQuery, id);
    }

    public static final class TeacherMapper implements RowMapper<Teacher> {

        @Override
        public Teacher mapRow(ResultSet rs, int index) throws SQLException {
            Teacher t = new Teacher();
            t.setId(rs.getInt("id"));
            t.setFirstName(rs.getString("firstName"));
            t.setLastName(rs.getString("lastName"));
            t.setSpecialty(rs.getString("specialty"));

            return t;
        }
    }

}
