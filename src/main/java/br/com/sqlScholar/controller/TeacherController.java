package br.com.sqlScholar.controller;

import br.com.sqlScholar.model.Student;
import br.com.sqlScholar.model.Teacher;
import br.com.sqlScholar.repository.QuestionRepository;
import br.com.sqlScholar.repository.StudentRepository;
import br.com.sqlScholar.repository.TeacherRepository;
import br.com.sqlScholar.service.TeacherService;
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
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/tela_adicionar")
    public ModelAndView tela_adicionar(HttpSession session) {
        boolean logged = teacherService.verifySession(session);
        
        if (!logged) {
            return new ModelAndView("redirect:/");
        }else{
            if("teacher".equals(session.getAttribute("userType"))){
                Teacher teacher = (Teacher) session.getAttribute("userLogged");
                return new ModelAndView("redirect:/teacher/perfil/"+ teacher.getId());
            }
            return new ModelAndView("teacher/tela_adicionar", new HashMap<>());
        }
        
        
    }

    @GetMapping("/index")
    public ModelAndView index(HttpSession session) {
        boolean logged = teacherService.verifySession(session);
        
        if (logged == false || session.getAttribute("userType") != "admin") {
            return new ModelAndView("redirect:/home/index");
        }
        
        Map<String, Object> template = new HashMap<>();
        int pageNumber = 0;
        int pageSize = 15;
        template.put("arrTeacher", this.teacherService.pageableTeacher(pageNumber, pageSize));
        template.put("message", "");
        template.put("userLogged", session.getAttribute("userLogged"));
        template.put("userType", session.getAttribute("userType"));
        template.put("isAdmin", session.getAttribute("isAdmin"));
        return new ModelAndView("teacher/index", template);
    }

    @GetMapping("/perfil/{id}")
    public ModelAndView perfil(@PathVariable UUID id, HttpSession session) {

        boolean logged = teacherService.verifySession(session);
        Map<String, Object> template = new HashMap<>();
        if (!logged) {
            return new ModelAndView("redirect:/");
        }
        if("admin".equals(session.getAttribute("userType"))){
            template.put("isAdmin", session.getAttribute("isAdmin"));
            Optional <Teacher> teacher = this.teacherRepository.findById(id);
            template.put("teacher", teacher);
            List<Integer> count_questions = questionRepository.countQuestionsTeachers(id);
            List<Integer> count_students = studentRepository.countStudents();
            template.put("xvalues", count_students.toString());
            template.put("yvalues", count_questions.toString());
            template.put("countStudents", count_students.toString());
            template.put("countQuestions", count_questions.toString());
        }else{
            Teacher teacher = (Teacher) session.getAttribute("userLogged");
            List<Integer> count_questions = questionRepository.countQuestionsTeachers(teacher.getId());
            List<Integer> count_students = studentRepository.countStudents();
            template.put("isTeacher", session.getAttribute("isTeacher"));
            template.put("teacher", teacher);
            template.put("xvalues", count_students.toString());
            template.put("yvalues", count_questions.toString());
            template.put("countStudents", count_students.toString());
            template.put("countQuestions", count_questions.toString());
        } 
       
        
        template.put("message", "");
        return new ModelAndView("teacher/perfil", template);
    }

    @PostMapping("/adicionar")
    public ModelAndView adicionar(@ModelAttribute Teacher teacher) {
        String hashed = DigestUtils.md5DigestAsHex(teacher.getPassword().getBytes()).toUpperCase();
        teacher.setPassword(hashed);
        this.teacherRepository.save(teacher);
        Map<String, Object> template = new HashMap<>();
        template.put("message", "Professor criado com sucesso!");
        template.put("arrTeacher", this.teacherRepository.listAll());
        return new ModelAndView("teacher/perfil/" + teacher.getId(), template);
    }

    @RequestMapping("/editar")
    public ModelAndView editar(@RequestParam UUID id, @RequestParam String email, @RequestParam String firstName,
            @RequestParam String lastName, @RequestParam String password, @RequestParam String username) {
        Optional<Teacher> teacher = this.teacherRepository.findById(id);
        teacher.get().setId(id);
        teacher.get().setEmail(email);
        teacher.get().setFirstName(firstName);
        teacher.get().setLastName(lastName);
        teacher.get().setPassword(password);
        teacher.get().setUsername(username);
        this.teacherRepository.save(teacher.get());
        Map<String, Object> template = new HashMap<>();
        template.put("arrTeacher", this.teacherRepository.listAll());
        template.put("message", "Professor editado com sucesso!");
        return new ModelAndView("teacher/perfil/" + id, template);
    }

    @GetMapping("/tela_editar/{id}")
    public ModelAndView tela_editar(@PathVariable UUID id, HttpSession session) {
        boolean logged = teacherService.verifySession(session);

        if (!logged) {
            return new ModelAndView("redirect:/");
        }
        Map<String, Object> template = new HashMap<>();

        if (session.getAttribute("userType") == "student") {
            template.put("isTeacher", session.getAttribute("isTeacher"));
            Teacher teacher = (Teacher) session.getAttribute("userLogged");
            template.put("teacher", teacher);
        }else{
            template.put("isAdmin", session.getAttribute("isAdmin"));
            Optional<Teacher> teacher = this.teacherRepository.findById(id);
            template.put("teacher", teacher.get());
        }
        
        template.put("userLogged", session.getAttribute("userLogged"));
        template.put("userType", session.getAttribute("userType"));
        return new ModelAndView("/teacher/tela_editar", template);
    }

    @GetMapping("/deletar/{id}")
    public ModelAndView deletar(@PathVariable UUID id, HttpSession session) {
        boolean logged = teacherService.verifySession(session);
        Map<String, Object> template = new HashMap<>();
        if (!logged) {
            return new ModelAndView("redirect:/");
        }
        if (session.getAttribute("userType") == "student") {
            Teacher teacher = (Teacher) session.getAttribute("userLogged");
            template.put("isTeacher", session.getAttribute("isTeacher"));
            this.teacherRepository.deleteById(teacher.getId());
        }else{
            template.put("isAdmin", session.getAttribute("isAdmin"));
            this.teacherRepository.deleteById(id);
        }

        this.teacherRepository.deleteById(id);        
        template.put("arrTeacher", this.teacherRepository.listAll());
        template.put("message", "Professor deletado com sucesso!");
        template.put("userLogged", session.getAttribute("userLogged"));
        template.put("userType", session.getAttribute("userType"));
        if ("teacher".equals(session.getAttribute("userType"))) {
            session.invalidate();            
        }
        return new ModelAndView("redirect:/", template);
    }

}
