package com.sg.M4L3classroster.controller;

import com.sg.M4L3classroster.dao.CourseDAO;
import com.sg.M4L3classroster.dao.StudentDAO;
import com.sg.M4L3classroster.dao.TeacherDAO;
import com.sg.M4L3classroster.model.Student;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    Set<ConstraintViolation<Student>> violations = new HashSet<>();

    @GetMapping("students")
    public String displayStudents(Model model) {
        List students = studentDao.readAllStudents();
        model.addAttribute("students", students);

        model.addAttribute("errors", violations);

        return "students";
    }

    @PostMapping("addStudent")
    public String addStudent(String firstName, String lastName) {
        Student student = new Student();
        student.setFirstName(firstName);
        student.setLastName(lastName);

        //validation using constraints from dto
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(student);

        if (violations.isEmpty()) {
            studentDao.createStudent(student);
        }

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
    public String performEditStudent(@Valid Student student, BindingResult result) {
        if (result.hasErrors()) {
            return "editStudent";
        }
        
        studentDao.updateStudent(student);
        return "redirect:/students";
    }
}
