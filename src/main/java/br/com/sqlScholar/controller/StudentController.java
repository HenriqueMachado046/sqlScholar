package br.com.sqlScholar.controller;

import br.com.sqlScholar.model.Student;
import br.com.sqlScholar.repository.QuestionRepository;
import br.com.sqlScholar.repository.StudentRepository;
import br.com.sqlScholar.service.StudentService;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/tela_adicionar")
    public ModelAndView tela_adicionar(){
        return new ModelAndView("student/tela_adicionar", new HashMap<>());
    }

    @GetMapping("/index")
    public ModelAndView index(HttpSession session){
        boolean logged = studentService.verifySession(session);
        System.out.println(session.getAttribute("userType"));
        if (logged == false || session.getAttribute("userType") != "admin") {
            return new ModelAndView("redirect:/home/index");
        }
        
        Map<String, Object> template = new HashMap<>();
        int pageNumber = 0;
        int pageSize = 15;
        template.put("message", "");
        template.put("arrStudent", this.studentService.pageableStudent(pageNumber, pageSize));
        template.put("userLogged", session.getAttribute("userLogged"));
        template.put("userType", session.getAttribute("userType"));
        template.put("isAdmin", session.getAttribute("isAdmin"));
        return new ModelAndView("student/index", template);
    }

    @GetMapping("/perfil/{id}")
    public ModelAndView perfil(@PathVariable UUID id, HttpSession session){
        boolean logged = studentService.verifySession(session);
        if (logged == false) {
            return new ModelAndView("redirect:/");
        }
    
        Map<String, Object> template = new HashMap<>();
       
        if ("admin".equals(session.getAttribute("userType"))) {
            Optional<Student> student = this.studentRepository.findById(id);
            Integer right = studentRepository.getRightById(student.get().getId());
            Integer wrong = studentRepository.getWrongById(student.get().getId());
            Integer attempts = wrong + right;
            template.put("student", student);
            template.put("wrongQuestions", wrong.toString());
            template.put("rightQuestions", right.toString());
            template.put("attemptedQuestions", attempts.toString());
            template.put("isAdmin", session.getAttribute("isAdmin"));
        }else{
            Student student = (Student) session.getAttribute("userLogged");
            Integer right = studentRepository.getRightById(student.getId());
            Integer wrong = studentRepository.getWrongById(student.getId());
            Integer attempts = wrong + right;
            template.put("student", student);
            template.put("wrongQuestions", wrong.toString());
            template.put("rightQuestions", right.toString());
            template.put("attemptedQuestions", attempts.toString());
            template.put("isStudent", session.getAttribute("isStudent"));
        }

        List<Integer> yValues = questionRepository.countQuestions();//Quantidade de questões;

        template.put("userLogged", session.getAttribute("userLogged"));
        template.put("userType", session.getAttribute("userType"));       
        template.put("yvalues", yValues.toString());
        return new ModelAndView("student/perfil", template);
    }
    
    @PostMapping("/adicionar")
    public ModelAndView adicionar(@ModelAttribute Student student){
        String hashed = DigestUtils.md5DigestAsHex(student.getPassword().getBytes()).toUpperCase();        
        student.setPassword(hashed);
        this.studentRepository.save(student);
        Map<String, Object> template = new HashMap<>();
        template.put("message", "Aluno criado com sucesso!");
        return new ModelAndView("redirect:/student/perfil/" + student.getId(), template);
    }

    @RequestMapping("/editar")
    public ModelAndView editar(@RequestParam UUID id, @RequestParam String email,
     @RequestParam String firstName, @RequestParam String lastName, @RequestParam String password, @RequestParam String username){
        Optional<Student> student = this.studentRepository.findById(id);
        student.get().setId(id);
        student.get().setEmail(email);
        student.get().setFirstName(firstName);
        student.get().setLastName(lastName);
        student.get().setPassword(password);
        student.get().setUsername(username);
        this.studentRepository.save(student.get());
        Map<String, Object> template = new HashMap<>();        
        template.put("message", "Aluno editado com sucesso!");
        template.put("arrStudent", this.studentRepository.listAll());
        return new ModelAndView("redirect:/student/perfil/"+id, template);
    }

    @GetMapping("/tela_editar/{id}")
    public ModelAndView tela_editar(@PathVariable UUID id, HttpSession session){
        boolean logged = studentService.verifySession(session);

        if (logged == false) {
            return new ModelAndView("redirect:/");
        }

        Map<String, Object> template = new HashMap<>();

        if (session.getAttribute("userType") == "student") {
            template.put("isStudent", session.getAttribute("isStudent"));
            Student student = (Student) session.getAttribute("userLogged");
            template.put("student", student);
        }else{
            template.put("isAdmin", session.getAttribute("isAdmin"));
            Optional<Student> student = this.studentRepository.findById(id);
            template.put("student", student.get());
        }
        
        template.put("userLogged", session.getAttribute("userLogged"));
        template.put("userType", session.getAttribute("userType"));
        return new ModelAndView("/student/tela_editar", template);
    }

    @GetMapping("/deletar/{id}")
    public ModelAndView deletar(@PathVariable UUID id, HttpSession session){
        boolean logged = studentService.verifySession(session);
        if (logged == false) {
            return new ModelAndView("redirect:/");
        }
        Map<String, Object> template = new HashMap<>();
        if (session.getAttribute("userType") == "student") {
            Student student = (Student) session.getAttribute("userLogged");
            template.put("isStudent", session.getAttribute("isStudent"));
            this.studentRepository.deleteById(student.getId());
        }else{
            template.put("isAdmin", session.getAttribute("isAdmin"));
            this.studentRepository.deleteById(id);
        }        
        
        template.put("message", "Aluno deletado com sucesso!");
        template.put("arrStudent", this.studentRepository.listAll());
        template.put("userLogged", session.getAttribute("userLogged"));
        template.put("userType", session.getAttribute("userType"));
        if ("student".equals(session.getAttribute("userType"))) {
            session.invalidate();            
        }
        return new ModelAndView("redirect:/", template);
    }

}