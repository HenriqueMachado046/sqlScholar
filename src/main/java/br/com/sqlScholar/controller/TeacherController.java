package br.com.sqlScholar.controller;

import br.com.sqlScholar.model.Teacher;
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
        
        if (logged == false && session.getAttribute("userType") != "admin") {
            return new ModelAndView("redirect:/");
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
            Optional <Teacher> teacher = this.teacherRepository.findById(id);
            template.put("teacher", teacher);
        }else{
            Teacher teacher = (Teacher) session.getAttribute("userLogged");
            template.put("teacher", teacher);
        }
        
        List<Integer> xValues = new ArrayList<>();// Será substituido por acesso ao banco e dados que virão de lá
        List<Integer> yValues = new ArrayList<>();// Será substituido por acesso ao banco e dados que virão de lá
        int count_students = (int) (Math.random() * 20);// Será substituido por acesso ao banco e dados que virão de lá
                                                        // (contagem de alunos)
        int count_questions = (int) (Math.random() * 20);// Será substituido por acesso ao banco e dados que virão de lá
                                                         // (contagem de questões totais registradas)
        for (int i = 0; i < 5; i++) {
            xValues.add((int) (Math.random() * 200));
            yValues.add((int) (Math.random() * 100));
        }
        template.put("xvalues", xValues.toString());
        template.put("yvalues", yValues.toString());
        template.put("countStudents", count_students);
        template.put("countQuestions", count_questions);
        
        template.put("message", "");
        return new ModelAndView("teacher/perfil/" + id, template);
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
        Optional<Teacher> teacher = this.teacherRepository.findById(id);
        template.put("teacher", teacher.get());
        return new ModelAndView("/teacher/tela_editar", template);
    }

    @GetMapping("/deletar/{id}")
    public ModelAndView deletar(@PathVariable UUID id, HttpSession session) {
        boolean logged = teacherService.verifySession(session);
        if (!logged) {
            return new ModelAndView("redirect:/");
        }
        this.teacherRepository.deleteById(id);
        Map<String, Object> template = new HashMap<>();
        template.put("arrTeacher", this.teacherRepository.listAll());
        template.put("message", "Professor deletado com sucesso!");
        if ("teacher".equals(session.getAttribute("userType"))) {
            session.invalidate();            
        }
        return new ModelAndView("redirect:/", template);
    }

}
