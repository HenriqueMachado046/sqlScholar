package br.com.sqlScholar.controller;

import br.com.sqlScholar.model.Student;
import br.com.sqlScholar.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired

    @GetMapping("/tela_adicionar")
    public ModelAndView tela_adicionar(){
        return new ModelAndView("student/tela_adicionar", new HashMap<>());
    }

    @GetMapping("/index")
    public ModelAndView index(){
        Map<String, Object> template = new HashMap<>();
        template.put("arrStudent", this.studentRepository.listAll());
        return new ModelAndView("student/index", template);
    }

    @PostMapping("/adicionar")
    public ModelAndView adicionar(@ModelAttribute Student student){
        this.studentRepository.save(student);
        Map<String, Object> template = new HashMap<>();
        template.put("message", "Aluno criado com sucesso!");
        return new ModelAndView("student/message", template);
    }

    @RequestMapping("/editar")
    public ModelAndView editar(@ModelAttribute Student student){
        System.out.println(student.getId());
        this.studentRepository.save(student);
        Map<String, Object> template = new HashMap<>();
        template.put("message", "Aluno editado com sucesso!");
        return new ModelAndView("student/message", template);
    }

    @GetMapping("/tela_editar/{id}")
    public ModelAndView tela_editar(@PathVariable UUID id){
        Map<String, Object> template = new HashMap<>();
        Optional<Student> student = this.studentRepository.findById(id);
        System.out.println(student.toString());
        template.put("student",  student);
        return new ModelAndView("/student/tela_editar", template);
    }

    @GetMapping("/deletar/{id}")
    public ModelAndView deletar(@PathVariable UUID id){
        this.studentRepository.deleteById(id);
        Map<String, Object> template = new HashMap<>();
        template.put("message", "Aluno deletado com sucesso!");
        return new ModelAndView("student/message", template);
    }

}