package com.sg.classroster.controllers;

import com.sg.classroster.daos.CourseDao;
import com.sg.classroster.daos.StudentDao;
import com.sg.classroster.daos.TeacherDao;
import com.sg.classroster.dtos.Student;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
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

/**
 *
 * @author rhash
 */
@Controller
public class StudentController {

    @Autowired
    TeacherDao teacherDao;

    @Autowired
    StudentDao studentDao;

    @Autowired
    CourseDao courseDao;
    
    Set<ConstraintViolation<Student>> violations = new HashSet<>();

    @GetMapping("students")
    public String displayStudents(Model model) {
        List students = studentDao.getAllStudents();
        model.addAttribute("students", students);
        return "students";
    }

    //Using raw data
    /*
    @PostMapping("addStudent")
    public String addStudent(String firstName, String lastName) {
        Student student = new Student();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        studentDao.addStudent(student);

        return "redirect:/students";
    }
*/
    
    //using model
    @PostMapping("addStudent")
    public String addStudent(HttpServletRequest request){
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        
        Student s = new Student();
        s.setFirstName(firstName);
        s.setLastName(lastName);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(s);

        if (violations.isEmpty()) {
            studentDao.addStudent(s);
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
        Student student = studentDao.getStudentById(id);
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
