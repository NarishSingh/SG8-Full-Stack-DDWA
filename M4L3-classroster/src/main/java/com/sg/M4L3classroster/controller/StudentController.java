package com.sg.M4L3classroster.controller;

import com.sg.M4L3classroster.dao.CourseDAO;
import com.sg.M4L3classroster.dao.StudentDAO;
import com.sg.M4L3classroster.dao.TeacherDAO;
import com.sg.M4L3classroster.model.Student;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StudentController {

    @Autowired
    TeacherDAO teacherDao;

    @Autowired
    StudentDAO studentDao;

    @Autowired
    CourseDAO courseDao;
    
    @GetMapping("students")
    public String displayStudents(Model model) {
        List students = studentDao.readAllStudents();
        model.addAttribute("students", students);
        return "students";
    }
    
    @PostMapping("addStudent")
    public String addStudent(String firstName, String lastName) {
        Student student = new Student();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        studentDao.createStudent(student);
        
        return "redirect:/students";
    }
    
    @GetMapping("deleteStudent")
    public String deleteStudent(Integer id) {
        studentDao.deleteStudentById(id);
        return "redirect:/students";
    }
    
    @GetMapping("editStudent")
    public String editStudent(Integer id, Model model) {
        Student student = studentDao.readStudentById(id);
        model.addAttribute("student", student);
        return "editStudent";
    }
    
    @PostMapping("editStudent")
    public String performEditStudent(Student student) {
        studentDao.updateStudent(student);
        return "redirect:/students";
    }
}
