package br.com.sqlScholar.controller;


import br.com.sqlScholar.dto.DifficultyDTO;
import br.com.sqlScholar.dto.TeacherDTO;
import br.com.sqlScholar.model.Question;
import br.com.sqlScholar.model.Teacher;
import br.com.sqlScholar.repository.QuestionRepository;
import br.com.sqlScholar.repository.TeacherRepository;

import br.com.sqlScholar.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/tela_adicionar")
    public ModelAndView tela_adicionar(){
        List<Teacher> teacher = this.teacherRepository.findAll();
        Map<String, Object> template = new HashMap<>();
        template.put("arrTeacher", teacher);
        return new ModelAndView("question/tela_adicionar", template);
    }

    @GetMapping("/index")
    public ModelAndView index(){
        int pageNumber = 0;
        int pageSize = 15;
        Map<String, Object> template =  new HashMap<>();
        template.put("message","");
        template.put("arrQuestion", this.questionService.pageableQuestion(pageNumber, pageSize));
        return new ModelAndView("question/index", template);
    }

    @GetMapping("mostrar_questao/{id}")
    public ModelAndView mostrarQuestao(@PathVariable UUID id){
        Map<String, Object> template = new HashMap<>();
        Optional<Question> question = this.questionRepository.findById(id);
        template.put("question", question.get());
        return new ModelAndView("question/mostrar_questao", template);
    }


    @GetMapping("tela_corrigir/{id}")
    public ModelAndView tela_corrigir(@PathVariable UUID id){
        Map<String, Object> template = new HashMap<>();
        Optional<Question> question = this.questionRepository.findById(id);
        template.put("question", question.get());
        //Este aqui passa a informação para a ação de corrigir;
        return new ModelAndView("question/tela_corrigir", template);
    }

    @PostMapping("/corrigir/{id}")
    public ModelAndView corrigir(@PathVariable UUID id, @RequestParam String sqlStudent){
        Optional<Question> question = this.questionRepository.findById(id);
        
        //Primeiro é preciso resolver a EXECUÇÃO DE SQL. Só depois de resolvido, poderá ser utilizado o método de resolução.
        //O execução do SQL será feita de maneira independente, pois é uma coisa que será útil em algumas outras partes do código.
        //Atualmente, isto não faz nada. O código será escrito em QuestionService e importado, para melhor legibilidade.

        Map<String, Object> template =  new HashMap<>();
        template.put("message","Erro/Acerto");
        return new ModelAndView("question/message", template);
    }

    @PostMapping("/adicionar")
    public ModelAndView adicionar(@RequestParam String title, @RequestParam String sql,
    @RequestParam String difficulty,@RequestParam String awnser ,@RequestParam String description,
    @RequestParam UUID teacher_id){

        Question question = new Question();
        Optional <Teacher> teacher = this.teacherRepository.findById(teacher_id);

        question.setTitle(title);
        question.setDifficulty(difficulty);
        question.setDescription(description);
        question.setAnswer(awnser);
        question.setSql(sql);
        question.setOwner(teacher.get());
        
        this.questionRepository.save(question);

        Map<String, Object> template =  new HashMap<>();
        template.put("message","Questão cadastrada com sucesso!");
        template.put("arrQuestion", this.questionRepository.findAll());
        return new ModelAndView("question/index", template);
    }

    //correto
    @RequestMapping("/editar")
    public ModelAndView editar(@RequestParam String title, @RequestParam String sql,
    @RequestParam String difficulty,@RequestParam String answer ,@RequestParam String description ,@RequestParam UUID id,
    @RequestParam UUID teacher_id){
        Optional<Question> question = this.questionRepository.findById(id);
        Optional <Teacher> teacher = this.teacherRepository.findById(teacher_id);
        question.get().setId(id);
        question.get().setTitle(title);
        question.get().setDifficulty(difficulty);
        question.get().setDescription(description);
        question.get().setAnswer(answer);
        question.get().setSql(sql);
        question.get().setOwner(teacher.get());

        this.questionRepository.save(question.get());
        Map<String, Object> template = new HashMap<>();        
        template.put("arrQuestion", this.questionRepository.findAll());
        template.put("message", "Questão editada com sucesso!");
        return new ModelAndView("question/index", template);
    }

    @GetMapping("/tela_responder/{id}")
    public ModelAndView tela_responder(@PathVariable UUID id) {
        Map<String, Object> template =  new HashMap<>();
        Optional<Question> question = this.questionRepository.findById(id);
        template.put("question", question.get());
        return new ModelAndView("question/tela_responder", template);
    }

    @RequestMapping("/responder")
    public ModelAndView responder(@RequestParam String resposta) {
        //O banco de dados deverá ser passado por aqui. O @RequestParam irá receber uma string com o nome do banco.
        Map<String, Object> template =  new HashMap<>();
        System.out.println("Aqui?");
        //questionService.awnserQuestion(resposta);
        List<String> respostas = questionService.awnserQuestion(resposta);
        for (int i = 0; i < respostas.size(); i++) {
            System.out.println(respostas.get(i));
        }
        template.put("resposta", respostas);
        return new ModelAndView("question/resposta", template);
    }
    
    

    @GetMapping("/tela_editar/{id}")
    public ModelAndView tela_editar (@PathVariable UUID id){
        Map<String, Object> template =  new HashMap<>();
        Optional<Question> question = this.questionRepository.findById(id);

        List<TeacherDTO> vetTeacherDTOs = new ArrayList<TeacherDTO>();       
        TeacherDTO owner = new TeacherDTO();
        owner.setId(question.get().getOwner().getId());
        owner.setFirstName(question.get().getOwner().getFirstName());
        owner.setLastName(question.get().getOwner().getLastName());
        owner.setOwner(true);
        List<Teacher> vetTeacher = this.teacherRepository.findAll();
        for (int i = 0; i < vetTeacher.size(); i++){
            TeacherDTO teacherDTO = new TeacherDTO();
            teacherDTO.setId(vetTeacher.get(i).getId());
            teacherDTO.setFirstName(vetTeacher.get(i).getFirstName());
            teacherDTO.setLastName(vetTeacher.get(i).getLastName());
            if (vetTeacher.get(i).getId() != owner.getId()) {            
                teacherDTO.setOwner(false);            
            } else {
                teacherDTO.setOwner(true);
            }
            vetTeacherDTOs.add(teacherDTO);    
        }

        List<DifficultyDTO> vDifficulties = new ArrayList<>();
        vDifficulties.add(new DifficultyDTO("Fácil", "Fácil".toUpperCase(), ((question.get().getDifficulty().equals("FÁCIL")) ? true : false)));
        vDifficulties.add(new DifficultyDTO("Intermediário", "Intermediário".toUpperCase(), ((question.get().getDifficulty().equals("INTERMEDIÁRIO")) ? true : false)));
        vDifficulties.add(new DifficultyDTO("Difícil", "Difícil".toUpperCase(), ((question.get().getDifficulty().equals("DIFÍCIL")) ? true : false)));
        template.put("question", question.get());
        template.put("vetProfessor", vetTeacherDTOs);        
        template.put("vetDificuldade", vDifficulties);        
        return new ModelAndView("question/tela_editar", template);
    }

    @GetMapping("/deletar/{id}")
    public ModelAndView deletar(@PathVariable UUID id){
        this.questionRepository.deleteById(id);
        Map<String, Object> template = new HashMap<>();
        template.put("arrQuestion", this.questionRepository.findAll());
        template.put("message", "Questão deletada com sucesso!");
        return new ModelAndView("question/index", template);
    }

}
