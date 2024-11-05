package br.com.sqlScholar.controller;

import br.com.sqlScholar.dto.QuestionDTO;
import br.com.sqlScholar.dto.TeacherDTO;
import br.com.sqlScholar.model.Question;
import br.com.sqlScholar.model.QuestionList;
import br.com.sqlScholar.model.Teacher;
import br.com.sqlScholar.repository.QuestionListRepository;
import br.com.sqlScholar.repository.QuestionRepository;
import br.com.sqlScholar.repository.TeacherRepository;
import br.com.sqlScholar.service.QuestionListService;
import br.com.sqlScholar.service.QuestionService;
import br.com.sqlScholar.service.TeacherService;

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

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private QuestionListService questionListService;

    @Autowired
    private QuestionService questionService;

    // PENDENTE: filtrar por somente pelas propria questões do professor e as que forem públicas ≥ pendente
    // Henrique: Filtro criado. Só está comentado, pois só poderá ser ativado após ser criado o login.
    @GetMapping("/tela_adicionar")
    public ModelAndView tela_adicionar(){        
        List<Question> question = this.questionRepository.findAll();
        //List<Question> question = this.questionRepository.listAllSharedAndOwned(id);
        //Teacher teacher = this.teacherRepository.findById(id).get();
        List<Teacher> teacher = this.teacherRepository.findAll();
        Map<String, Object> template = new HashMap<>();
        template.put("arrQuestion", question);
        template.put("arrTeacher", teacher);
        return new ModelAndView("questionlist/tela_adicionar", template) ;
    }

    // problema paginacao
    @GetMapping("/index")
    public ModelAndView index(){
        Map<String, Object> template = new HashMap<>();
        // int pageNumber = 0;
        // int pageSize = 15;
        template.put("message", "");
        // template.put("arrQuestionList", questionListService.pageableQuestionList(pageNumber, pageSize));
        // template.put("pageNumber", pageNumber + 1);
        template.put("arrQuestionList", questionListRepository.findAll());
        // template.put("pageNumber", "");
        return new ModelAndView("questionlist/index", template);
    }

    @PostMapping("/adicionar")    
    public ModelAndView adicionar(@RequestParam String title, @RequestParam String database_script, @RequestParam Boolean isPrivate, @RequestParam List<UUID> question_id, @RequestParam UUID teacher_id){        
        QuestionList questionList = new QuestionList();
        questionList.setTitle(title);
        questionList.setPrivate(isPrivate);               
        questionList.setDatabaseScript(database_script.trim());                       

        Optional<Teacher> teacher = this.teacherRepository.findById(teacher_id);                
        questionList.setOwner(teacher.get());                     
        this.questionListRepository.save(questionList);        
        for (int i = 0; i < question_id.size(); i++) {
            this.questionListRepository.insertQuestions(questionList.getId(), question_id.get(i));    
        }                        
        // TODO: pra coisas pequenas, funciona!
        this.questionListService.rodeSQL("CREATE database list_"+questionList.getId().toString().replace("-", "")+";");  
        this.questionListService.rodeSQL(database_script.trim(), "list_"+questionList.getId().toString().replace("-", ""));        
        
        Map<String, Object> template = new HashMap<>();
        template.put("message", "Lista cadastrada com sucesso!");
        // template.put("pageNumber", "");
        template.put("arrQuestionList", this.questionListRepository.listAll());
        return new ModelAndView("questionlist/index", template);
    }

    @RequestMapping("/editar")
    public ModelAndView editar(@RequestParam UUID id, @RequestParam String database_script, @RequestParam String title, @RequestParam Boolean isPrivate, @RequestParam UUID teacher_id, @RequestParam List<UUID> question_id){
        QuestionList questionList = this.questionListRepository.findById(id).get();
        questionList.setTitle(title);
        questionList.setPrivate(isPrivate);               
        questionList.setOwner(this.teacherRepository.findById(teacher_id).get());
        
        // vais dropar o banco antigo? ou editar não aceita substituir a base de dados? => pendente
        questionList.setDatabaseScript(database_script);        

        //  TODO: bug
        // this.questionListService.rodeSQL("DROP SCHEMA public CASCADE;", "list_"+questionList.getId().toString().replace("-", ""));        
        // this.questionListService.rodeSQL("CREATE SCHEMA public;", "list_"+questionList.getId().toString().replace("-", ""));        
        // this.questionListService.rodeSQL(database_script.trim(), "list_"+questionList.getId().toString().replace("-", ""));            

        this.questionListRepository.save(questionList);
        this.questionListRepository.deleteQuestions(id);

        for (int i = 0; i < question_id.size(); i++) {
            this.questionListRepository.insertQuestions(questionList.getId(), question_id.get(i));    
        }            
        Map<String, Object> template = new HashMap<>();
        template.put("message", "Lista editada com sucesso!");
        template.put("arrQuestionList", this.questionListRepository.listAll());
        return new ModelAndView("questionlist/index", template);
    }

    @GetMapping("/tela_editar/{id}")
    public ModelAndView tela_editar (@PathVariable UUID id){
        Map<String, Object> template = new HashMap<>();

        Optional<QuestionList> questionList = this.questionListRepository.findById(id);

        List<TeacherDTO> arrTeacherDTOs = this.teacherService.listAvailableTeachers(questionList.get().getOwner().getId(), 
        questionList.get().getOwner().getFirstName(), questionList.get().getOwner().getLastName());

        //Pegar somente questões do professor e questões compartilhadas
        List<QuestionDTO> vetQuestionDTOs = this.questionService.listAvailableQuestions();
        

        for (QuestionDTO vetQuestionDTO : vetQuestionDTOs) {
            for (int i = 0; i < questionList.get().getQuestions().size(); i++) {
                if (vetQuestionDTO.getId() == questionList.get().getQuestions().get(i).getId()) {
                    vetQuestionDTO.setEnabled(true);
                }
            }
        }

        template.put("vetProfessor", arrTeacherDTOs);     
        template.put("arrQuestion", vetQuestionDTOs);
        template.put("title", questionList.get().getTitle());
        template.put("id", questionList.get().getId());
        template.put("database_script", questionList.get().getDatabaseScript());

        return new ModelAndView("questionlist/tela_editar", template);
    }

    @GetMapping("/deletar/{id}")
    public ModelAndView deletar(@PathVariable UUID id){
        this.questionListRepository.deletar(id);
        try {
            // TODO: verificar
            this.questionListService.rodeSQL("DROP DATABASE list_"+id.toString().replace("-", "")+";");    
        } catch (Exception e) {
            System.out.println("===========");
            System.out.println("Não foi possível deletar o banco correspondente:list_"+id.toString());
            System.out.println("===========");
        }                
        Map<String, Object> template = new HashMap<>();
        template.put("arrQuestionList", this.questionListRepository.listAll());
        template.put("message", "Lista deletada com sucesso!" + id);
        return new ModelAndView("questionlist/index", template);
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