package com.sg.M4L3classroster.dao;

import com.sg.M4L3classroster.model.Course;
import com.sg.M4L3classroster.model.Student;
import com.sg.M4L3classroster.model.Teacher;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
public class CourseDAOTest {

    @Autowired
    TeacherDAO teacherDAO;

    @Autowired
    StudentDAO studentDAO;

    @Autowired
    CourseDAO courseDAO;

    public CourseDAOTest() {
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
     * Test of createCourse and readCourseById method, of class CourseDAO.
     */
    @Test
    public void testCreateReadCourseById() {
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

        Course fromDao = courseDAO.readCourseById(c.getId());
        assertEquals(c, fromDao);
    }

    /**
     * Test of readAllCourses method, of class CourseDAO.
     */
    @Test
    public void testReadAllCourses() {
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

        Course c2 = new Course();
        c2.setName("Test Course2");
        c2.setTeacher(t);
        c2.setStudents(students);
        c2 = courseDAO.createCourse(c2);

        List<Course> courses = courseDAO.readAllCourses();

        assertEquals(2, courses.size());
        assertTrue(courses.contains(c));
        assertTrue(courses.contains(c2));
    }

    /**
     * Test of updateCourse method, of class CourseDAO.
     */
    @Test
    public void testUpdateCourse() {
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

        Course fromDao = courseDAO.readCourseById(c.getId());
        assertEquals(c, fromDao);

        c.setName("New Course Name");
        Student s2 = new Student();
        s2.setFirstName("first2");
        s2.setLastName("last2");
        s2 = studentDAO.createStudent(s2);
        students.add(s2);
        c.setStudents(students);

        courseDAO.updateCourse(c);

        assertNotEquals(c, fromDao);

        fromDao = courseDAO.readCourseById(c.getId());
        assertEquals(c, fromDao);
    }

    /**
     * Test of deleteCourseById method, of class CourseDAO.
     */
    @Test
    public void testDeleteCourseById() {
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

        Course fromDao = courseDAO.readCourseById(c.getId());
        assertEquals(c, fromDao);

        courseDAO.deleteCourseById(c.getId());

        fromDao = courseDAO.readCourseById(c.getId());
        assertNull(fromDao);
    }

    /**
     * Test of readCoursesForTeacher method, of class CourseDAO.
     */
    @Test
    public void testReadCoursesForTeacher() {
        Teacher t1 = new Teacher();
        t1.setFirstName("Test First");
        t1.setLastName("Test Last");
        t1.setSpecialty("Test Specialty");
        t1 = teacherDAO.createTeacher(t1);

        Teacher t2 = new Teacher();
        t2.setFirstName("Test First2");
        t2.setLastName("Test Last2");
        t2.setSpecialty("Test Specialty2");
        t2 = teacherDAO.createTeacher(t2);

        Student s = new Student();
        s.setFirstName("Test Student First");
        s.setLastName("Test Student Last");
        s = studentDAO.createStudent(s);
        List<Student> students = new ArrayList<>();
        students.add(s);

        Course c1 = new Course();
        c1.setName("Test Course");
        c1.setTeacher(t1);
        c1.setStudents(students);
        c1 = courseDAO.createCourse(c1);

        Course c2 = new Course();
        c2.setName("Test Course2");
        c2.setTeacher(t2); //NOT t #1
        c2.setStudents(students);
        c2 = courseDAO.createCourse(c2);

        Course c3 = new Course();
        c3.setName("Test Course3");
        c3.setTeacher(t1);
        c3.setStudents(students);
        c3 = courseDAO.createCourse(c3);

        List<Course> courses = courseDAO.readCoursesForTeacher(t1);

        assertEquals(2, courses.size());
        assertTrue(courses.contains(c1));
        assertFalse(courses.contains(c2));
        assertTrue(courses.contains(c3));
    }

    /**
     * Test of readCoursesForStudent method, of class CourseDAO.
     */
    @Test
    public void testReadCoursesForStudent() {
        Teacher t = new Teacher();
        t.setFirstName("Test First");
        t.setLastName("Test Last");
        t.setSpecialty("Test Specialty");
        t = teacherDAO.createTeacher(t);

        Student s1 = new Student();
        s1.setFirstName("Test Student First");
        s1.setLastName("Test Student Last");
        s1 = studentDAO.createStudent(s1);

        Student s2 = new Student();
        s2.setFirstName("Test Student First2");
        s2.setLastName("Test Student Last2");
        s2 = studentDAO.createStudent(s2);

        List<Student> students = new ArrayList<>();
        students.add(s1);
        students.add(s2);

        List<Student> students2 = new ArrayList<>();
        students2.add(s2);

        Course c1 = new Course();
        c1.setName("Test Course");
        c1.setTeacher(t);
        c1.setStudents(students);
        c1 = courseDAO.createCourse(c1);

        Course c2 = new Course();
        c2.setName("Test Course2");
        c2.setTeacher(t);
        c2.setStudents(students);
        c2 = courseDAO.createCourse(c2);

        Course c3 = new Course();
        c3.setName("Test Course3");
        c3.setTeacher(t);
        c3.setStudents(students2); //ONLY w s2
        c3 = courseDAO.createCourse(c3);

        List<Course> courses = courseDAO.readCoursesForStudent(s1);
        assertEquals(2, courses.size());
        assertTrue(courses.contains(c1));
        assertTrue(courses.contains(c2));
        assertFalse(courses.contains(c3));
    }

}
