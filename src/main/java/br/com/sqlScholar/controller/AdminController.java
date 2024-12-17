package br.com.sqlScholar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.sqlScholar.repository.StudentRepository;
import br.com.sqlScholar.repository.TeacherRepository;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    public TeacherRepository teacherRepository;

    @Autowired
    public StudentRepository studentRepository;

    @GetMapping("/index")
    public ModelAndView home(HttpSession session){
        if (!"admin".equals(session.getAttribute("userType"))) {
            return new ModelAndView("redirect:/login");            
        }
        return new ModelAndView("index");
    }
}