package br.com.sqlScholar.controller;

import br.com.sqlScholar.model.Question;
import br.com.sqlScholar.model.QuestionList;
import br.com.sqlScholar.model.Teacher;
import br.com.sqlScholar.repository.QuestionListRepository;
import br.com.sqlScholar.repository.QuestionRepository;
import br.com.sqlScholar.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@RestController
@RequestMapping("/questionlist")
public class QuestionListController {

    @Autowired
    private QuestionListRepository questionListRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    // filtrar por somente pelas proprias questões do professor e as que forem compartilhaveis (publicas) -> pendente
    @GetMapping("/tela_adicionar")
    public ModelAndView tela_adicionar(){        
        List<Question> question = this.questionRepository.findAll();
        List<Teacher> teacher = this.teacherRepository.findAll();
        Map<String, Object> template = new HashMap<>();
        template.put("arrQuestion", question);
        template.put("arrTeacher", teacher);
        return new ModelAndView("questionlist/tela_adicionar", template) ;
    }

    @GetMapping("/index")
    public ModelAndView index(){
        Map<String, Object> template = new HashMap<>();
        template.put("arrQuestionList", this.questionListRepository.listAll());
        return new ModelAndView("questionlist/index", template);
    }

    @PostMapping("/adicionar")    
    public ModelAndView adicionar(@RequestParam String title, @RequestParam List<UUID> question_id, @RequestParam UUID teacher_id){        
        QuestionList questionList = new QuestionList();
        questionList.setTitle(title);
        Optional<Teacher> teacher = this.teacherRepository.findById(teacher_id);                
        questionList.setTeacher(teacher.get());                     
        this.questionListRepository.save(questionList);
        for (int i = 0; i < question_id.size(); i++) {
            this.questionListRepository.insertQuestions(questionList.getId(), question_id.get(i));    
        }        
        Map<String, Object> template = new HashMap<>();
        template.put("message", "Lista cadastrada com sucesso!");
        return new ModelAndView("questionlist/message", template);
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
        this.questionListRepository.deletar(id);
        // this.questionListRepository.deleteById(id);
        Map<String, Object> template = new HashMap<>();
        template.put("message", "Lista deletada com sucesso!" + id);
        return new ModelAndView("questionlist/message", template);
    }

    @GetMapping("/mostrar_lista/{id}")
    public ModelAndView mostrarLista(@PathVariable UUID id){
        Map<String, Object> template = new HashMap<>();
        Optional<QuestionList> questionlist = this.questionListRepository.findById(id);
        
        template.put("questionlist", questionlist.get());
        template.put("questions", questionlist.get().getQuestions());
        return new ModelAndView("questionlist/mostrar_lista", template);
    }
}