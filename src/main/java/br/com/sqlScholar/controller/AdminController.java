package br.com.sqlScholar.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        //Como o Mustache não tem lógica, só dá pra negar a exibição de campos com coisas como esse isAdmin;
        boolean isAdmin = true;
       
        Map<String, Object> template = new HashMap<>();
        Object userLogged = session.getAttribute("userLogged");
        String userType = (String) session.getAttribute("userType");
        template.put("userLogged", userLogged);
        template.put("userType", userType);
        template.put ("isAdmin", isAdmin);
        return new ModelAndView("admin/index", template);
    }

}