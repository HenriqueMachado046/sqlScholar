package br.com.sqlScholar.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/home")
public class HomeController {
    
    @GetMapping("/index")
    public ModelAndView home(HttpSession session){
        
        Map<String, Object> template = new HashMap<>();
        Object userLogged = session.getAttribute("userLogged");
        String userType = (String) session.getAttribute("userType");
        
        if (userType.equals("admin")) {
            template.put ("isAdmin", session.getAttribute("isAdmin"));
        }else{
            if (userType.equals("teacher")) {
                template.put ("isTeacher", session.getAttribute("isTeacher"));                
            }else{
                template.put ("isStudent", session.getAttribute("isStudent"));
            }
        }

        template.put("userLogged", userLogged);
        template.put("userType", userType);
        return new ModelAndView("home/index", template);
    }
}
