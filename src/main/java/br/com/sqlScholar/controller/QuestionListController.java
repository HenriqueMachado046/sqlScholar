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
    private QuestionListService questionListService;

    // PENDENTE: filtrar por somente pelas propria questões do professor e as que
    // forem públicas ≥ pendente
    // Henrique: Filtro criado. Só está comentado, pois só poderá ser ativado após
    // ser criado o login.
    @GetMapping("/tela_adicionar")
    public ModelAndView tela_adicionar() {
        List<Question> question = this.questionRepository.findAll();
        // List<Question> question = this.questionRepository.listAllSharedAndOwned(id);
        // Teacher teacher = this.teacherRepository.findById(id).get();
        List<Teacher> teacher = this.teacherRepository.findAll();
        Map<String, Object> template = new HashMap<>();
        template.put("arrQuestion", question);
        template.put("arrTeacher", teacher);
        return new ModelAndView("questionlist/tela_adicionar", template);
    }

    @GetMapping("/index")
    public ModelAndView index() {
        Map<String, Object> template = new HashMap<>();
        // int pageNumber = 0;
        // int pageSize = 15;
        template.put("message", "");
        // template.put("arrQuestionList",
        // questionListService.pageableQuestionList(pageNumber, pageSize));
        // template.put("pageNumber", pageNumber + 1);
        template.put("arrQuestionList", questionListRepository.listPublic());
        // template.put("pageNumber", "");
        return new ModelAndView("questionlist/index", template);
    }

    @GetMapping("/tela_testar/{id}")
    public ModelAndView tela_testar(@PathVariable UUID id) {
        Map<String, Object> template = new HashMap<>();
        Optional<QuestionList> questionlist = questionListRepository.findById(id);
        template.put("questionlist", questionlist.get());
        return new ModelAndView("questionlist/tela_testar", template);
    }

    @RequestMapping("/testar_sql")
    public ModelAndView sql_teste(@RequestParam String sql_teste, @RequestParam UUID id) {
        Map<String, Object> template = new HashMap<>();
        sql_teste = sql_teste.toLowerCase();
        Optional<QuestionList> questionlist = questionListRepository.findById(id);
        // Abaixo é a maneira de fazer o acesso do banco através da questionlist. Este
        // mesmo método deverá ser utilizado durante a criação das questões.
        List<String> resultadoTeste = questionListService.rodeSQL(sql_teste, questionlist.get().getDatabaseName());
        for (int i = 0; i < resultadoTeste.size(); i++) {
            System.out.println(resultadoTeste.get(i));
        }
        template.put("sql_teste", resultadoTeste);
        return new ModelAndView("questionlist/testar_sql", template);
    }

    @PostMapping("/adicionar")
    public ModelAndView adicionar(@RequestParam String title,
            @RequestParam String database_script,
            @RequestParam UUID teacher_id,
            @RequestParam String database_name,
            @RequestParam String description,
            @RequestParam Boolean isPrivate) {
        QuestionList questionList = new QuestionList();
        database_name = database_name.replaceAll(" ", "");
        database_name = database_name.toLowerCase();
        System.out.println(database_name);
        questionList.setTitle(title);
        questionList.setDatabaseScript(database_script.trim().toLowerCase());
        questionList.setDatabaseName(database_name);
        questionList.setDescription(description);
        questionList.setPrivate(isPrivate);
        Optional<Teacher> teacher = this.teacherRepository.findById(teacher_id);
        questionList.setOwner(teacher.get());
        this.questionListRepository.save(questionList);
        // O nome do banco de dados será utilizado na criação. Após isso, o
        // database_script é executado.
        // O banco só deverá ser criado com os CREATE TABLES. Isto estará explícito no
        // HTML.
        // Agora está funcional.
        // Alterar para receber o create database do HTML.

        this.questionListService.createDatabase("CREATE DATABASE " + database_name + ";");
        this.questionListService.rodeSQL(database_script, questionList.getDatabaseName());
        // this.questionListService.rodeSQL(database_script.trim(),
        // "list_"+questionList.getId().toString().replace("-", ""));

        Map<String, Object> template = new HashMap<>();
        template.put("message", "Lista cadastrada com sucesso!");
        // template.put("pageNumber", "");
        template.put("arrQuestionList", this.questionListRepository.listAll());
        return new ModelAndView("questionlist/index", template);
    }

    @RequestMapping("/editar")
    public ModelAndView editar(@RequestParam UUID id, @RequestParam String database_script, @RequestParam String title,
            @RequestParam Boolean isPrivate, @RequestParam String description) {
        QuestionList questionList = this.questionListRepository.findById(id).get();
        questionList.setTitle(title);
        questionList.setPrivate(isPrivate);
        // vais dropar o banco antigo? ou editar não aceita substituir a base de dados?
        // => pendente
        // Não aceita substituir. A lista deve ser excluída.
        questionList.setDatabaseScript(database_script);
        // this.questionListService.rodeSQL("DROP SCHEMA public CASCADE;",
        // "list_"+questionList.getId().toString().replace("-", ""));
        // this.questionListService.rodeSQL("CREATE SCHEMA public;",
        // "list_"+questionList.getId().toString().replace("-", ""));
        // this.questionListService.rodeSQL(database_script.trim(),
        // "list_"+questionList.getId().toString().replace("-", ""));
        this.questionListRepository.save(questionList);
        Map<String, Object> template = new HashMap<>();
        template.put("message", "Lista editada com sucesso!");
        template.put("arrQuestionList", this.questionListRepository.listAll());
        return new ModelAndView("questionlist/index", template);
    }

    @GetMapping("/tela_editar/{id}")
    public ModelAndView tela_editar(@PathVariable UUID id) {
        Map<String, Object> template = new HashMap<>();

        Optional<QuestionList> questionList = this.questionListRepository.findById(id);
        template.put("title", questionList.get().getTitle());
        template.put("id", questionList.get().getId());
        template.put("database_script", questionList.get().getDatabaseScript());
        template.put("description", questionList.get().getDescription());

        return new ModelAndView("questionlist/tela_editar", template);
    }

    @GetMapping("/deletar/{id}")
    public ModelAndView deletar(@PathVariable UUID id) {
        Optional<QuestionList> questionList = this.questionListRepository.findById(id);
        this.questionListRepository.delete(questionList.get());
        try {
            this.questionListService.createDatabase("DROP DATABASE " + questionList.get().getDatabaseName());
        } catch (Exception e) {
            System.out.println("===========");
            System.out
                    .println("Não foi possível deletar o banco correspondente:" + questionList.get().getDatabaseName());
            System.out.println("===========");
        }
        Map<String, Object> template = new HashMap<>();
        template.put("arrQuestionList", this.questionListRepository.listAll());
        template.put("message", "Lista deletada com sucesso!");
        return new ModelAndView("questionlist/index", template);
    }

    @GetMapping("/mostrar_lista/{id}")
    public ModelAndView mostrarLista(@PathVariable UUID id) {
        // List<String> resultado;
        // Trocar o "qualquer coisa" pelo input SQL do professor.
        // resultado = this.questionListService.rodeSQL("qualquer coisa", "sqlscholar");
        Map<String, Object> template = new HashMap<>();
        Optional<QuestionList> questionlist = this.questionListRepository.findById(id);
        template.put("questionlist", questionlist.get());
        template.put("questions", questionlist.get().getQuestions());
        // template.put("resultado", resultado);
        return new ModelAndView("questionlist/mostrar_lista", template);
    }
}