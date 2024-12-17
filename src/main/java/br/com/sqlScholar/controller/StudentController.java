package br.com.sqlScholar.controller;

import br.com.sqlScholar.model.Student;
import br.com.sqlScholar.repository.StudentRepository;
import br.com.sqlScholar.service.StudentService;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    @GetMapping("/tela_adicionar")
    public ModelAndView tela_adicionar(){
        return new ModelAndView("student/tela_adicionar", new HashMap<>());
    }

    @GetMapping("/index")
    public ModelAndView index(HttpSession session){
        boolean logged = studentService.verifySession(session);
        if (!logged) {
            return new ModelAndView("redirect:/");
        }
        Map<String, Object> template = new HashMap<>();
        int pageNumber = 0;
        int pageSize = 15;
        template.put("message", "");
        template.put("arrStudent", this.studentService.pageableStudent(pageNumber, pageSize));
        return new ModelAndView("student/index", template);
    }

    @GetMapping("/perfil/{id}")
    public ModelAndView perfil(@PathVariable UUID id, HttpSession session){
        boolean logged = studentService.verifySession(session);
        if (!logged) {
            return new ModelAndView("redirect:/");
        }
        Map<String, Object> template = new HashMap<>();
        if ("admin".equals(session.getAttribute("userType"))) {
            Optional<Student> student = this.studentRepository.findById(id);
            template.put("student", student.get());
        }else{
            Student student = (Student) session.getAttribute("userLogged");
            template.put("student", student);
        }

        List<Integer> xValues = new ArrayList<>();//Será substituido por acesso ao banco e dados que virão de lá
        List<Integer> yValues = new ArrayList<>();//Será substituido por acesso ao banco e dados que virão de lá
        int count_solved = (int)(Math.random() * 30);//Será substituido por acesso ao banco e dados que virão de lá (Contagem de questões resolvidas pelo aluno)
        for (int i = 0; i < 5; i++) {
            xValues.add((int)(Math.random() * 200));
            yValues.add((int)(Math.random() * 100));
        }
       
        template.put("xvalues", xValues.toString());
        template.put("yvalues", yValues.toString());
        template.put("solvedQuestions", count_solved);
        return new ModelAndView("student/perfil", template);
    }
    
    @PostMapping("/adicionar")
    public ModelAndView adicionar(@ModelAttribute Student student){
        String hashed = DigestUtils.md5DigestAsHex(student.getPassword().getBytes()).toUpperCase();        
        student.setPassword(hashed);
        this.studentRepository.save(student);
        Map<String, Object> template = new HashMap<>();
        template.put("message", "Aluno criado com sucesso!");
        template.put("arrStudent", this.studentRepository.listAll());

        return new ModelAndView("redirect:/student/perfil/"+student.getId(), template);
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
        if (!logged) {
            return new ModelAndView("redirect:/");
        }
        Map<String, Object> template = new HashMap<>();
        Optional<Student> student = this.studentRepository.findById(id);
        template.put("student", student.get());
        return new ModelAndView("/student/tela_editar", template);
    }

    @GetMapping("/deletar/{id}")
    public ModelAndView deletar(@PathVariable UUID id, HttpSession session){
        boolean logged = studentService.verifySession(session);
        if (!logged) {
            return new ModelAndView("redirect:/");
        }
        this.studentRepository.deleteById(id);
        Map<String, Object> template = new HashMap<>();
        template.put("message", "Aluno deletado com sucesso!");
        template.put("arrStudent", this.studentRepository.listAll());

        return new ModelAndView("redirect:/", template);
    }

}