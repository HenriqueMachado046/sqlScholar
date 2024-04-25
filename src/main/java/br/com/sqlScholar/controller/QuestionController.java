package br.com.sqlScholar.controller;


import br.com.sqlScholar.model.Question;
import br.com.sqlScholar.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/tela_adicionar")
    public ModelAndView tela_adicionar(){
        return new ModelAndView("question/tela_adicionar", new HashMap<>());
    }

    @GetMapping("/index")
    public ModelAndView index(){
        Map<String, Object> template =  new HashMap<>();
        template.put("arrQuestion", this.questionRepository.listAll());
        return new ModelAndView("question/index", template);
    }

    @GetMapping("mostrar_questao/{id}")
    public ModelAndView mostrarQuestao(@PathVariable UUID id){
        Map<String, Object> template = new HashMap<>();
        Optional<Question> question = this.questionRepository.findById(id);
        template.put("question", question);
        System.out.println(question.toString());
        return new ModelAndView("question/mostrar_questao", template);
    }

    @PostMapping("/adicionar")
    public ModelAndView adicionar(@ModelAttribute Question question){
        this.questionRepository.save(question);
        Map<String, Object> template =  new HashMap<>();
        template.put("message","Questão cadastrada com sucesso!");
        return new ModelAndView("question/message", template);
    }

    @RequestMapping("/editar")
    public ModelAndView editar(@ModelAttribute Question question){
        this.questionRepository.save(question);
        Map<String, Object> template = new HashMap<>();
        template.put("message", "Questão editada com sucesso!");
        return new ModelAndView("question/message", template);
    }

    @GetMapping("/tela_editar/{id}")
    public ModelAndView tela_editar (@PathVariable UUID id){
        Map<String, Object> template =  new HashMap<>();
        Optional<Question> question = this.questionRepository.findById(id);
        template.put("question", question);
        return new ModelAndView("question/tela_editar", template);
    }

    @GetMapping("/deletar/{id}")
    public ModelAndView deletar(@PathVariable UUID id){
        this.questionRepository.deleteById(id);
        Map<String, Object> template = new HashMap<>();
        template.put("message", "Questão deletada com sucesso!");
        return new ModelAndView("question/message");
    }

}
