package ua.foxminded.universitycms.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.foxminded.universitycms.service.StudentService;

@Controller
@RequestMapping("/students-page")
public class StudentController {
	
	 @Autowired
	 private StudentService studentService;
	 
	 @GetMapping("/allStudents")
	 public String students(Model model) throws SQLException{
		 model.addAttribute("students", studentService.getAllStudents());
		 return "students-page";
	 }
}
