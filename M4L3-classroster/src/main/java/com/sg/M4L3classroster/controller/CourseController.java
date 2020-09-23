package com.sg.M4L3classroster.controller;

import com.sg.M4L3classroster.dao.CourseDAO;
import com.sg.M4L3classroster.dao.StudentDAO;
import com.sg.M4L3classroster.dao.TeacherDAO;
import com.sg.M4L3classroster.model.Course;
import com.sg.M4L3classroster.model.Student;
import com.sg.M4L3classroster.model.Teacher;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CourseController {

    @Autowired
    TeacherDAO teacherDao;

    @Autowired
    StudentDAO studentDao;

    @Autowired
    CourseDAO courseDao;

    @GetMapping("courses")
    public String displayCourses(Model model) {
        List<Course> courses = courseDao.readAllCourses();
        List<Teacher> teachers = teacherDao.readAllTeachers();
        List<Student> students = studentDao.readAllStudents();

        model.addAttribute("courses", courses);
        model.addAttribute("teachers", teachers);
        model.addAttribute("students", students);

        return "courses";
    }

    @PostMapping("addCourse")
    public String addCourse(Course course, HttpServletRequest request) {
        String teacherId = request.getParameter("teacherId");
        String[] studentIds = request.getParameterValues("studentId");

        course.setTeacher(teacherDao.readTeacherById(Integer.parseInt(teacherId)));

        List<Student> students = new ArrayList<>();
        for (String studentId : studentIds) {
            students.add(studentDao.readStudentById(Integer.parseInt(studentId)));
        }

        course.setStudents(students);
        courseDao.createCourse(course);

        return "redirect:/courses";
    }

    @GetMapping("courseDetail")
    public String courseDetail(Integer id, Model model) {
        Course course = courseDao.readCourseById(id);
        model.addAttribute("course", course);

        return "courseDetail";
    }

    @GetMapping("deleteCourse")
    public String deleteCourse(Integer id) {
        courseDao.deleteCourseById(id);

        return "redirect:/courses";
    }

    @GetMapping("editCourse")
    public String editCourse(Integer id, Model model) {
        Course course = courseDao.readCourseById(id);
        List<Student> students = studentDao.readAllStudents();
        List<Teacher> teachers = teacherDao.readAllTeachers();

        model.addAttribute("course", course);
        model.addAttribute("students", students);
        model.addAttribute("teachers", teachers);

        return "editCourse";
    }

    @PostMapping("editCourse")
    public String performEditCourse(@Valid Course course, BindingResult result,
            HttpServletRequest request, Model model) {
        String teacherId = request.getParameter("teacherId");
        String[] studentIds = request.getParameterValues("studentId");

        course.setTeacher(teacherDao.readTeacherById(Integer.parseInt(teacherId)));

        List<Student> students = new ArrayList<>();
        if (studentIds != null) {
            for (String studentId : studentIds) {
                students.add(studentDao.readStudentById(Integer.parseInt(studentId)));
            }
        } else {
            FieldError error = new FieldError("course", "students", "Must include at least 1 student");
            result.addError(error);
        }
        
        course.setStudents(students);
        
        if (result.hasErrors()) {
            model.addAttribute("teachers", teacherDao.readAllTeachers());
            model.addAttribute("students", studentDao.readAllStudents());
            model.addAttribute("course", course);
            
            return "editCourse";
        }
        
        courseDao.updateCourse(course);
        
        return "redirect:/courses";
    }
}
