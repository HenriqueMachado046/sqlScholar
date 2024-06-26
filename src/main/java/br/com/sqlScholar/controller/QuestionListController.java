package br.com.sqlScholar.controller;

import br.com.sqlScholar.dto.QuestionDTO;
import br.com.sqlScholar.dto.TeacherDTO;
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
        template.put("message", "");
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
        template.put("arrQuestionList", this.questionListRepository.listAll());

        return new ModelAndView("questionlist/index", template);
    }

    @RequestMapping("/editar")
    public ModelAndView editar(@RequestParam UUID id, @RequestParam String title, @RequestParam Boolean isPrivate, @RequestParam UUID teacher_id, @RequestParam List<UUID> question_id){
        QuestionList questionList = this.questionListRepository.findById(id).get();
        questionList.setTitle(title);
        questionList.setTeacher(this.teacherRepository.findById(teacher_id).get());
        questionList.setPrivate(isPrivate);               
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

        List<TeacherDTO> vetTeacherDTOs = new ArrayList<TeacherDTO>();       
        TeacherDTO owner = new TeacherDTO();
        owner.setId(questionList.get().getTeacher().getId());
        owner.setFirstName(questionList.get().getTeacher().getFirstName());
        owner.setLastName(questionList.get().getTeacher().getLastName());
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
        template.put("vetProfessor", vetTeacherDTOs);     

        // otimizar isso
        List<Question> vetQuestion = this.questionRepository.findAll();
        List<QuestionDTO> vetQuestionDTOs = new ArrayList<QuestionDTO>();   
        for (int j = 0; j < vetQuestion.size(); j++){
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setId(vetQuestion.get(j).getId());
            questionDTO.setTitle(vetQuestion.get(j).getTitle());
            questionDTO.setEnabled(false);  
            vetQuestionDTOs.add(questionDTO);
        }
        for (int j = 0; j < vetQuestionDTOs.size(); j++){
            for (int i = 0; i < questionList.get().getQuestions().size(); i++){                          
                if (vetQuestionDTOs.get(j).getId() == questionList.get().getQuestions().get(i).getId()) {            
                    vetQuestionDTOs.get(j).setEnabled(true);                                                         
                }
            }        
        }
        template.put("arrQuestion", vetQuestionDTOs);
        template.put("title", questionList.get().getTitle());
        template.put("id", questionList.get().getId());
        return new ModelAndView("questionlist/tela_editar", template);
    }

    @GetMapping("/deletar/{id}")
    public ModelAndView deletar(@PathVariable UUID id){
        this.questionListRepository.deletar(id);
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