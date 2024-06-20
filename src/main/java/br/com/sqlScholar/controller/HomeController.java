package br.com.sqlScholar.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping
public class HomeController {
    
    @GetMapping("/")
    public ModelAndView home(){
        Map<String, Object> template =  new HashMap<>();
        return new ModelAndView("home/index", template);
    }
}
