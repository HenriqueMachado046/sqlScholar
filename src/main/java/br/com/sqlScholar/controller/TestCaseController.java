package br.com.sqlScholar.controller;

import br.com.sqlScholar.dto.QuestionDTO;
import br.com.sqlScholar.model.Question;
import br.com.sqlScholar.model.TestCase;
import br.com.sqlScholar.repository.QuestionRepository;
import br.com.sqlScholar.repository.TestCaseRepository;
import br.com.sqlScholar.service.QuestionService;
import br.com.sqlScholar.service.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping("/test")
public class TestCaseController {
    @Autowired
    private TestCaseRepository testCaseRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private TestCaseService testCaseService;

    @GetMapping("/tela_adicionar")
    public ModelAndView tela_adicionar(){
        List<Question> question = this.questionRepository.findAll();
        Map<String, Object> template = new HashMap<>();
        template.put("arrQuestion", question);
        return new ModelAndView("test/tela_adicionar", template);
    }

    @GetMapping("/index")
    public ModelAndView index(){
        Map<String, Object> template = new HashMap<>();
        int pageNumber = 0;
        int pageSize = 15;
        template.put("arrTest", this.testCaseService.pageableTestCase(pageNumber, pageSize));
        return new ModelAndView("test/index", template);
    }

    @GetMapping("/mostrar_test/{id}")
    public ModelAndView mostrarTest(@PathVariable UUID id){
        Map<String, Object> template = new HashMap<>();
        Optional<TestCase> test = this.testCaseRepository.findById(id);
        template.put("test", test.get());
        template.put("question_id", test.get().getQuestion().getId());
        return new ModelAndView("test/mostrar_test", template);
    }

    @PostMapping("/adicionar")
    public ModelAndView adicionar(@ModelAttribute TestCase test, @RequestParam UUID question_id){
        Optional<Question> question = this.questionRepository.findById(question_id);
        test.setQuestion(question.get());
        this.testCaseRepository.save(test);
        Map<String, Object> template =  new HashMap<>();
        template.put("message","Teste cadastrado com sucesso!");
        return new ModelAndView("test/message", template);
    }

    @RequestMapping("/editar")
    public ModelAndView editar(@RequestParam UUID id, @RequestParam Question question, @RequestParam String teste){
        Optional<TestCase> test = this.testCaseRepository.findById(id);
        test.get().setId(id);
        test.get().setQuestion(question);
        test.get().setTeste(teste);
        this.testCaseRepository.save(test.get());
        Map<String, Object> template = new HashMap<>();
        template.put("message", "Teste editado com sucesso!");
        return new ModelAndView("test/message", template);
    }

    @GetMapping("/tela_editar/{id}")
    public ModelAndView tela_editar (@PathVariable UUID id){
        Map<String, Object> template =  new HashMap<>();
        Optional<TestCase> test = this.testCaseRepository.findById(id);
        List<QuestionDTO> questionDTOS = this.questionService.listAvailableQuestions();
        template.put("test", test.get());
        template.put("arrQuestion", questionDTOS);
        return new ModelAndView("test/tela_editar", template);
    }

    @GetMapping("/deletar/{id}")
    public ModelAndView deletar(@PathVariable UUID id){
        this.testCaseRepository.deleteById(id);
        Map<String, Object> template = new HashMap<>();
        template.put("message", "Teste deletado com sucesso!");
        return new ModelAndView("test/message", template);
    }


}
