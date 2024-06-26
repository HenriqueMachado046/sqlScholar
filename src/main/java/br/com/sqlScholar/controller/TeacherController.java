package br.com.sqlScholar.controller;


import br.com.sqlScholar.model.Teacher;
import br.com.sqlScholar.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/teacher")
public class TeacherController {


    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired

    @GetMapping("/tela_adicionar")
    public ModelAndView tela_adicionar(){
        return new ModelAndView("teacher/tela_adicionar", new HashMap<>());
    }

    @GetMapping("/index")
    public ModelAndView index(){        
        Map<String, Object> template = new HashMap<>();
        template.put("arrTeacher", this.teacherRepository.listAll());
        template.put("message", "");
        return new ModelAndView("teacher/index", template);
    }

    @PostMapping("/adicionar")
    public ModelAndView adicionar(@ModelAttribute Teacher teacher){
        this.teacherRepository.save(teacher);
        Map<String, Object> template = new HashMap<>();
        template.put("message", "Professor criado com sucesso!");
        template.put("arrTeacher", this.teacherRepository.listAll());
        return new ModelAndView("teacher/index", template);
    }

    @RequestMapping("/editar")
    public ModelAndView editar(@RequestParam UUID id, @RequestParam String email, @RequestParam String firstName,
     @RequestParam String lastName, @RequestParam String password, @RequestParam String username){
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
        return new ModelAndView("teacher/index", template);
    }

    @GetMapping("/tela_editar/{id}")
    public ModelAndView tela_editar(@PathVariable UUID id){
        Map<String, Object> template = new HashMap<>();
        Optional<Teacher> teacher = this.teacherRepository.findById(id);
        template.put("teacher",  teacher.get());
        return new ModelAndView("/teacher/tela_editar", template);
    }

    @GetMapping("/deletar/{id}")
    public ModelAndView deletar(@PathVariable UUID id){
        this.teacherRepository.deleteById(id);
        Map<String, Object> template = new HashMap<>();
        template.put("arrTeacher", this.teacherRepository.listAll());        
        template.put("message", "Professor deletado com sucesso!");
        return new ModelAndView("teacher/message", template);
    }

}
