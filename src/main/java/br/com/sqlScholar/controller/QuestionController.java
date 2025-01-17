package br.com.sqlScholar.controller;

import br.com.sqlScholar.dto.DifficultyDTO;
import br.com.sqlScholar.dto.TeacherDTO;
import br.com.sqlScholar.model.Question;
import br.com.sqlScholar.model.QuestionList;
import br.com.sqlScholar.model.Student;
import br.com.sqlScholar.model.Teacher;
import br.com.sqlScholar.repository.QuestionListRepository;
import br.com.sqlScholar.repository.QuestionRepository;
import br.com.sqlScholar.repository.StudentRepository;
import br.com.sqlScholar.repository.TeacherRepository;

import br.com.sqlScholar.service.QuestionService;
import br.com.sqlScholar.service.StudentService;
import br.com.sqlScholar.service.TeacherService;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionListRepository questionListRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/tela_adicionar/{questionlistId}")
    public ModelAndView tela_adicionar(@PathVariable UUID questionlistId, HttpSession session) {

        boolean logged = teacherService.verifySession(session);
        if (logged == false) {
            return new ModelAndView("redirect:/");                        
        }
        Map<String, Object> template = new HashMap<>();
        if ("admin".equals(session.getAttribute("userType"))) {
            template.put ("isAdmin", session.getAttribute("isAdmin"));
            List<Teacher> teacher = this.teacherRepository.findAll();
            template.put("arrTeacher", teacher);
        }else{
            template.put ("isTeacher", session.getAttribute("isTeacher"));
            Teacher teacher = (Teacher) session.getAttribute("userLogged");
            template.put("teacher", teacher);
        }

        Optional<QuestionList> questionlist = this.questionListRepository.findById(questionlistId);

        template.put("userLogged", session.getAttribute("userLogged"));
        template.put("userType", session.getAttribute("userType"));
        
        template.put("questionlist", questionlist.get());
        System.out.println("\n" + questionlistId);
        return new ModelAndView("question/tela_adicionar", template);
    }

    @GetMapping("/index")
    public ModelAndView index(HttpSession session) {
        
        Map<String, Object> template = new HashMap<>();

        if (session.getAttribute("userType") == null) {
            return new ModelAndView("redirect:/");            
        }
        
        if (session.getAttribute("userType").equals("admin")) {
            template.put ("isAdmin", session.getAttribute("isAdmin"));
        }else{
            return new ModelAndView("redirect:/home/index");
        }

        template.put("userLogged", session.getAttribute("userLogged"));
        template.put("userType", session.getAttribute("userType"));
        int pageNumber = 0;
        int pageSize = 15;
        template.put("message", "");
        template.put("arrQuestion", this.questionService.pageableQuestion(pageNumber, pageSize));
        return new ModelAndView("question/index", template);
    }

    @GetMapping("mostrar_questao/{id}")
    public ModelAndView mostrarQuestao(@PathVariable UUID id, HttpSession session) {
        Map<String, Object> template = new HashMap<>();

        if (session.getAttribute("userType") == null) {
            return new ModelAndView("redirect:/");            
        }
        
        if (session.getAttribute("userType").equals("admin")) {
            boolean hasAccess = true;
            template.put ("hasAccess", hasAccess);
        }else{
            if (session.getAttribute("userType").equals("teacher")) {
                boolean hasAccess = true;
                template.put ("hasAccess", hasAccess);                
            }else{
                template.put ("isStudent", session.getAttribute("isStudent"));                    
            }
        }
        template.put("userLogged", session.getAttribute("userLogged"));
        template.put("userType", session.getAttribute("userType"));

        Optional<Question> question = this.questionRepository.findById(id);
        template.put("question", question.get());
        return new ModelAndView("question/mostrar_questao", template);
    }

    @GetMapping("tela_corrigir/{id}")
    public ModelAndView tela_corrigir(@PathVariable UUID id) {
        Map<String, Object> template = new HashMap<>();
        Optional<Question> question = this.questionRepository.findById(id);
        template.put("question", question.get());
        // Este aqui passa a informação para a ação de corrigir;
        return new ModelAndView("question/tela_corrigir", template);
    }

    @PostMapping("/adicionar")
    public ModelAndView adicionar(@RequestParam UUID questionlist_id, @RequestParam String title,
            @RequestParam String sql,
            @RequestParam String difficulty, @RequestParam String description,
            @RequestParam UUID teacher_id) {

        Question question = new Question();
        Optional<Teacher> teacher = this.teacherRepository.findById(teacher_id);
        Optional<QuestionList> questionlist = this.questionListRepository.findById(questionlist_id);
        question.setTitle(title);
        question.setDifficulty(difficulty);
        question.setDescription(description);
        question.setSql(sql);
        question.setQuestionList(questionlist.get());
        question.setOwner(teacher.get());
        this.questionRepository.save(question);

        Map<String, Object> template = new HashMap<>();
        template.put("message", "Questão cadastrada com sucesso!");
        template.put("arrQuestion", this.questionRepository.findAll());
        // bugado. Precisa retornar corretamente para a página principal.
        return new ModelAndView("redirect:/questionlist/mostrar_lista/" + questionlist_id, template);
    }

    // correto
    @RequestMapping("/editar")
    public ModelAndView editar(@RequestParam String title, @RequestParam String sql,
            @RequestParam String difficulty, @RequestParam String description, @RequestParam UUID id,
            @RequestParam UUID teacher_id) {
        Optional<Question> question = this.questionRepository.findById(id);
        Optional<Teacher> teacher = this.teacherRepository.findById(teacher_id);
        question.get().setId(id);
        question.get().setTitle(title);
        question.get().setDifficulty(difficulty);
        question.get().setDescription(description);
        question.get().setSql(sql);
        question.get().setOwner(teacher.get());

        this.questionRepository.save(question.get());
        Map<String, Object> template = new HashMap<>();
        template.put("arrQuestion", this.questionRepository.findAll());
        template.put("message", "Questão editada com sucesso!");
        return new ModelAndView("redirect:/questionlist/mostrar_lista/" + question.get().getQuestionList().getId(),
                template);
    }

    @GetMapping("/tela_responder/{id}")
    public ModelAndView tela_responder(@PathVariable UUID id, HttpSession session) {

        boolean logged = studentService.verifySession(session);

        if(logged == false){
            return new ModelAndView("redirect:/"); 
        }

        Map<String, Object> template = new HashMap<>();

        if ("admin".equals(session.getAttribute("userType"))) {
            template.put ("isAdmin", session.getAttribute("isAdmin"));
        }else{
            if ("student".equals(session.getAttribute("userType"))) {
                template.put ("isStudent", session.getAttribute("isStudent"));              
            }
        }

        template.put("userLogged", session.getAttribute("userLogged"));
        template.put("userType", session.getAttribute("userType"));

        
        Optional<Question> question = this.questionRepository.findById(id);
        Optional<QuestionList> questionlist = this.questionListRepository
                .findById(question.get().getQuestionList().getId());
        template.put("question", question.get());
        template.put("questionlist", questionlist.get());
        return new ModelAndView("question/tela_responder", template);
    }

    @RequestMapping("/responder")
    public ModelAndView responder(@RequestParam String resposta, @RequestParam String databaseName,
            @RequestParam UUID id, HttpSession session) {
        // O banco de dados deverá ser passado por aqui. O @RequestParam irá receber uma
        // string com o nome do banco.
        // Agora só falta achar uma maneira de fazer o nome do banco ser passado por
        // parâmetro. Quando resolver a inserção, resolve este por consequência.
        // Aprendendo com erros. Na verdade, é possível pegar o id da lista de questões
        // por dentro da questão, visto que são duas entidades que se relacionam.

        boolean logged = studentService.verifySession(session);

        if(logged == false){
            return new ModelAndView("redirect:/"); 
        }
       
        Map<String, Object> template = new HashMap<>();
       
        
        Optional<Question> question = this.questionRepository.findById(id);

        String sql_questao = question.get().getSql();
        List<String> respostas = new ArrayList<String>();
        String message = " ";

        try {
           respostas = questionService.awnserQuestion(resposta, sql_questao, databaseName);
           message = respostas.get(respostas.size() - 1);

        } catch (Exception e) {
           message = "Erro: select em branco.";
        }

        
        String corrigida = "";       

        if ("admin".equals(session.getAttribute("userType"))) {
            template.put ("isAdmin", session.getAttribute("isAdmin"));
        }else{
            if ("student".equals(session.getAttribute("userType"))) {
                Student student = (Student) session.getAttribute("userLogged");
                template.put ("isStudent", session.getAttribute("isStudent"));
                if (respostas.size() > 0) {
                    if (respostas.get(respostas.size() - 1).equals("Certo.")) {
                        respostas.remove(respostas.size() - 1);
                        studentRepository.updateRightById(student.getId());
                        teacherRepository.updateCounterById(question.get().getOwner().getId());
                    }else{
                        studentRepository.updateWrongById(student.getId());
                    }
                    for (int i = 0; i < respostas.size(); i++) {
                        corrigida += " [" + respostas.get(i) + "] \n";
                    }
                }else{
                    corrigida = "Não há resultados para retornar.";
                }
                
            }
       
        }


        template.put("resposta", resposta);
        template.put("corrigida", corrigida);
        template.put("message", message);
        template.put("questionlistid", question.get().getQuestionList().getId());
        return new ModelAndView("question/resposta", template);
    }

    @GetMapping("/tela_editar/{id}")
    public ModelAndView tela_editar(@PathVariable UUID id, HttpSession session) {
        Map<String, Object> template = new HashMap<>();
        boolean logged = teacherService.verifySession(session);
        if (logged == false) {
            return new ModelAndView("redirect:/");                        
        } 
        if ("admin".equals(session.getAttribute("userType"))) {
            template.put ("isAdmin", session.getAttribute("isAdmin"));
            List<Teacher> teacher = this.teacherRepository.findAll();
            template.put("arrTeacher", teacher);
        }else{
            template.put ("isTeacher", session.getAttribute("isTeacher"));
            Teacher teacher = (Teacher) session.getAttribute("userLogged");
            template.put("teacher", teacher);
        }
       
        Optional<Question> question = this.questionRepository.findById(id);

        List<TeacherDTO> vetTeacherDTOs = new ArrayList<TeacherDTO>();
        TeacherDTO owner = new TeacherDTO();
        owner.setId(question.get().getOwner().getId());
        owner.setFirstName(question.get().getOwner().getFirstName());
        owner.setLastName(question.get().getOwner().getLastName());
        owner.setOwner(true);

        List<DifficultyDTO> vDifficulties = new ArrayList<>();
        vDifficulties.add(new DifficultyDTO("Fácil", "Fácil".toUpperCase(),
                ((question.get().getDifficulty().equals("FÁCIL")) ? true : false)));
        vDifficulties.add(new DifficultyDTO("Intermediário", "Intermediário".toUpperCase(),
                ((question.get().getDifficulty().equals("INTERMEDIÁRIO")) ? true : false)));
        vDifficulties.add(new DifficultyDTO("Difícil", "Difícil".toUpperCase(),
                ((question.get().getDifficulty().equals("DIFÍCIL")) ? true : false)));
        template.put("question", question.get());
        template.put("vetProfessor", vetTeacherDTOs);
        template.put("vetDificuldade", vDifficulties);
        return new ModelAndView("question/tela_editar", template);
    }

    @GetMapping("/deletar/{id}")
    public ModelAndView deletar(@PathVariable UUID id, HttpSession session) {
        Optional<Question> question = this.questionRepository.findById(id);
        UUID id_lista = question.get().getQuestionList().getId();
        this.questionRepository.deleteById(id);
        Map<String, Object> template = new HashMap<>();
        if ("admin".equals(session.getAttribute("userType"))) {
            template.put ("isAdmin", session.getAttribute("isAdmin"));
        }else{
            template.put ("isTeacher", session.getAttribute("isTeacher"));   
        }
        template.put("userLogged", session.getAttribute("userLogged"));
        template.put("userType", session.getAttribute("userType"));
        template.put("arrQuestion", this.questionRepository.findAll());
        template.put("message", "Questão deletada com sucesso!");
        return new ModelAndView("redirect:/questionlist/mostrar_lista/" + id_lista, template);
    }

}
