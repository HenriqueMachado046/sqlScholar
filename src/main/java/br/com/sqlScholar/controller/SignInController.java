package br.com.sqlScholar.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.sqlScholar.model.Student;
import br.com.sqlScholar.repository.StudentRepository;
import br.com.sqlScholar.repository.TeacherRepository;

@Controller
@RequestMapping("/signin")
public class SignInController {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/student")
    public ModelAndView tela_cadastrar(){
        return new ModelAndView("signin/student", new HashMap<>());
    }

    @PostMapping("/adicionar")
    public ModelAndView adicionar(@ModelAttribute Student student){
        String hashed = DigestUtils.md5DigestAsHex(student.getPassword().getBytes()).toUpperCase();
        student.setPassword(hashed);
        this.studentRepository.save(student);
        Map<String, Object> template = new HashMap<>();
        template.put("message", "Cadastro concluido com sucesso.");        
        return new ModelAndView("redirect:/");        
    }
    
}
