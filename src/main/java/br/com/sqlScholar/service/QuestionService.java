package br.com.sqlScholar.service;

import br.com.sqlScholar.dto.QuestionDTO;
import br.com.sqlScholar.model.Question;
import br.com.sqlScholar.repository.QuestionRepository;
import br.com.sqlScholar.utils.sqlUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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

    public List<String> awnserQuestion(String sql){
        //Limitar o aluno a fazer apenas SELECT, com algum tipo de trava.
        ResultSet resultSet = sqlUtils.executeSQL(sql, "sqlscholar");
        String resultado = "";
        int count = 1;
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            
            List<String> resultadoList = new ArrayList<String>();
            while (resultSet.next()) {
               count = 1;
               resultado = "";
               while (count <= metaData.getColumnCount()) {
                   resultado += "\n" + metaData.getColumnName(count) + ": " + resultSet.getString(count).toString();
                   count++;
               }
               resultadoList.add(resultado);
            }
            return resultadoList;
        } catch (Exception e) {
            //Gambiarra.
            List<String> exceptionList = new ArrayList<String>();
            exceptionList.add(e.toString());
            return exceptionList;
        }
    }

}