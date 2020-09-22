package com.sg.M4L3classroster.dao;

import com.sg.M4L3classroster.model.Course;
import com.sg.M4L3classroster.model.Student;
import com.sg.M4L3classroster.model.Teacher;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentDAOTest {

    @Autowired
    TeacherDAO teacherDAO;

    @Autowired
    StudentDAO studentDAO;

    @Autowired
    CourseDAO courseDAO;

    public StudentDAOTest() {
    }

    @BeforeEach
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

    /**
     * Test of createStudent and readStudentById method, of class StudentDAO.
     */
    @Test
    public void testCreateReadStudentById() {
        Student s = new Student();
        s.setFirstName("First");
        s.setLastName("Last");
        s = studentDAO.createStudent(s);

        Student fromDao = studentDAO.readStudentById(s.getId());

        assertEquals(s, fromDao);
    }

    /**
     * Test of readAllStudents method, of class StudentDAO.
     */
    @Test
    public void testReadAllStudents() {
        Student s = new Student();
        s.setFirstName("First");
        s.setLastName("Last");
        s = studentDAO.createStudent(s);

        Student s2 = new Student();
        s2.setFirstName("First2");
        s2.setLastName("Last2");
        s2 = studentDAO.createStudent(s2);

        List<Student> students = studentDAO.readAllStudents();

        assertEquals(2, students.size());
        assertTrue(students.contains(s));
        assertTrue(students.contains(s2));
    }

    /**
     * Test of updateStudent method, of class StudentDAO.
     */
    @Test
    public void testUpdateStudent() {
        Student s = new Student();
        s.setFirstName("First");
        s.setLastName("Last");
        s = studentDAO.createStudent(s);

        Student fromDao = studentDAO.readStudentById(s.getId());
        assertEquals(s, fromDao);
        
        s.setFirstName("NewFirst");
        studentDAO.updateStudent(s);
        
        assertNotEquals(s, fromDao);
        
        fromDao = studentDAO.readStudentById(s.getId());
        assertEquals(s, fromDao);
    }

    /**
     * Test of deleteStudentById method, of class StudentDAO.
     */
    @Test
    public void testDeleteStudentById() {
        Teacher t = new Teacher();
        t.setFirstName("TestFirst");
        t.setLastName("testLast");
        t.setSpecialty("testSpec");
        t = teacherDAO.createTeacher(t);
        
        Student s = new Student();
        s.setFirstName("First");
        s.setLastName("Last");
        s = studentDAO.createStudent(s);
        List<Student> students = new ArrayList<>();
        students.add(s);
        
        Course c = new Course();
        c.setName("testC");
        c.setTeacher(t);
        c.setStudents(students);
        c = courseDAO.createCourse(c);
        
        Student fromDao = studentDAO.readStudentById(s.getId());
        
        assertEquals(s, fromDao);
        
        studentDAO.deleteStudentById(s.getId());
        
        fromDao = studentDAO.readStudentById(s.getId());
        assertNull(fromDao);
    }

}
