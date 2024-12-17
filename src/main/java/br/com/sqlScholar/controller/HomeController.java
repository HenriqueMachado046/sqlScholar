package br.com.sqlScholar.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/home")
public class HomeController {
    
    @GetMapping("/index")
    public ModelAndView home(){
        Map<String, Object> template =  new HashMap<>();
        return new ModelAndView("home/index", template);
    }
}
