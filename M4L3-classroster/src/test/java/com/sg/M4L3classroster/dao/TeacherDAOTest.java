package com.sg.M4L3classroster.dao;

import com.sg.M4L3classroster.model.Course;
import com.sg.M4L3classroster.model.Student;
import com.sg.M4L3classroster.model.Teacher;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeacherDAOTest {

    @Autowired
    TeacherDAO teacherDAO;

    @Autowired
    StudentDAO studentDAO;

    @Autowired
    CourseDAO courseDAO;

    public TeacherDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        List<Teacher> teachers = teacherDAO.readAllTeachers();
        for (Teacher t : teachers) {
            teacherDAO.deleteTeacherById(t.getId());
        }

        List<Student> students = studentDAO.readAllStudents();
        for (Student s : students) {
            studentDAO.deleteStudentById(s.getId());
        }

        List<Course> courses = courseDAO.readAllCourses();
        for (Course c : courses) {
            courseDAO.deleteCourseById(c.getId());
        }
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of readTeacherById method, of class TeacherDAO.
     */
    @Test
    public void testCreateReadTeacherById() {
        Teacher t = new Teacher();
        t.setFirstName("Test First");
        t.setLastName("Test Last");
        t.setSpecialty("Test Specialty");
        t = teacherDAO.createTeacher(t);

        Teacher fromDao = teacherDAO.readTeacherById(t.getId());

        assertEquals(t, fromDao);
    }

    /**
     * Test of readAllTeachers method, of class TeacherDAO.
     */
    @Test
    public void testReadAllTeachers() {
        Teacher t = new Teacher();
        t.setFirstName("Test First");
        t.setLastName("Test Last");
        t.setSpecialty("Test Specialty");
        t = teacherDAO.createTeacher(t);

        Teacher t2 = new Teacher();
        t2.setFirstName("Test First2");
        t2.setLastName("Test Last2");
        t2.setSpecialty("Test Specialty2");
        t2 = teacherDAO.createTeacher(t2);

        List<Teacher> teachers = teacherDAO.readAllTeachers();

        assertEquals(2, teachers.size());
        assertTrue(teachers.contains(t));
        assertTrue(teachers.contains(t2));
    }

    /**
     * Test of updateTeacher method, of class TeacherDAO.
     */
    @Test
    public void testUpdateTeacher() {
        Teacher t = new Teacher();
        t.setFirstName("Test First");
        t.setLastName("Test Last");
        t.setSpecialty("Test Specialty");
        t = teacherDAO.createTeacher(t);

        Teacher fromDao = teacherDAO.readTeacherById(t.getId());
        assertEquals(t, fromDao);

        t.setFirstName("New Test First");
        teacherDAO.updateTeacher(t);
        assertNotEquals(t, fromDao);

        fromDao = teacherDAO.readTeacherById(t.getId());
        assertEquals(t, fromDao);
    }

    /**
     * Test of deleteTeacherById method, of class TeacherDAO.
     */
    @Test
    public void testDeleteTeacherById() {
        Teacher t = new Teacher();
        t.setFirstName("Test First");
        t.setLastName("Test Last");
        t.setSpecialty("Test Specialty");
        t = teacherDAO.createTeacher(t);

        Student s = new Student();
        s.setFirstName("Test Student First");
        s.setLastName("Test Student Last");
        s = studentDAO.createStudent(s);
        List<Student> students = new ArrayList<>();
        students.add(s);

        Course c = new Course();
        c.setName("Test Course");
        c.setTeacher(t);
        c.setStudents(students);
        c = courseDAO.createCourse(c);

        Teacher fromDao = teacherDAO.readTeacherById(t.getId());
        assertEquals(t, fromDao);

        teacherDAO.deleteTeacherById(t.getId());

        fromDao = teacherDAO.readTeacherById(t.getId());
        assertNull(fromDao);
    }

}
