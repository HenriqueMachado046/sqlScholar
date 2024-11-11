package br.com.sqlScholar.service;

import br.com.sqlScholar.dto.QuestionDTO;
import br.com.sqlScholar.model.Question;
import br.com.sqlScholar.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public List<Question> pageableQuestion(int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Question> questionPage = this.questionRepository.findAll(pageable);
        return questionPage.getContent();
    }

    //funcionando
    public List<QuestionDTO> listAvailableQuestions(){
        List<Question> vetQuestion = this.questionRepository.findAll();
        List<QuestionDTO> vetQuestionDTOs = new ArrayList<QuestionDTO>();
        for (Question question : vetQuestion) {
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setId(question.getId());
            questionDTO.setTitle(question.getTitle());
            questionDTO.setEnabled(false);
            vetQuestionDTOs.add(questionDTO);
        }
        return vetQuestionDTOs;
    }

    public void awnserQuestion(UUID id){
        

    }

}