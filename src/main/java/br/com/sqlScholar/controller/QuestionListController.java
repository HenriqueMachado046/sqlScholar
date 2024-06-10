package br.com.sqlScholar.controller;

import br.com.sqlScholar.model.Question;
import br.com.sqlScholar.model.QuestionList;
import br.com.sqlScholar.repository.QuestionListRepository;
import br.com.sqlScholar.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/questionlist")
public class QuestionListController {

    @Autowired
    private QuestionListRepository questionListRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/tela_adicionar")
    public ModelAndView tela_adicionar(){
        return new ModelAndView("questionlist/tela_adicionar", new HashMap<>());
    }

    @GetMapping("/index")
    public ModelAndView index(){
        Map<String, Object> template = new HashMap<>();
        template.put("arrQuestionList", this.questionListRepository.listAll());
        return new ModelAndView("questionlist/index", template);
    }

    @PostMapping("/adicionar")
    public ModelAndView adicionar(@ModelAttribute QuestionList questionList, @RequestParam UUID question_id){
        Optional<Question> question = this.questionRepository.findById(question_id);
        questionList.setQuestions(this.questionRepository.findAll());
        this.questionListRepository.save(questionList);
        Map<String, Object> template = new HashMap<>();
        template.put("message", "Lista cadastrada com sucesso!");
        return new ModelAndView("questionlist/message");
    }

    @RequestMapping("/editar")
    public ModelAndView editar(@ModelAttribute QuestionList questionList){
        this.questionListRepository.save(questionList);
        Map<String, Object> template = new HashMap<>();
        template.put("message", "Lista editada com sucesso!");
        return new ModelAndView("questionlist/message", template);
    }

    @GetMapping("/tela_editar/{id}")
    public ModelAndView tela_editar (@PathVariable UUID id){
        Map<String, Object> template = new HashMap<>();
        Optional<QuestionList> questionList = this.questionListRepository.findById(id);
        template.put("questionlist", questionList.get());
        return new ModelAndView("questionlist/tela_editar", template);
    }

    @GetMapping("/deletar/{id}")
    public ModelAndView deletar(@PathVariable UUID id){
        this.questionListRepository.deleteById(id);
        Map<String, Object> template = new HashMap<>();
        template.put("message", "Lista deletada com sucesso!");
        return new ModelAndView("questionlist/message", template);
    }
}
