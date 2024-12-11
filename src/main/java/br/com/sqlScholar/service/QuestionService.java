package br.com.sqlScholar.service;

import br.com.sqlScholar.dto.QuestionDTO;
import br.com.sqlScholar.model.Question;
import br.com.sqlScholar.repository.QuestionRepository;
import br.com.sqlScholar.utils.Resultado;
import br.com.sqlScholar.utils.sqlUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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

    public List<String> awnserQuestion(String sql, String databaseName){
        //Limitar o aluno a fazer apenas SELECT, com algum tipo de trava.
        int count = 1;
        String resultadoString = "";

        //Deve funcionar...
        String sqlValidado = sqlUtils.validateSQL(sql);

        Resultado resultado = sqlUtils.executeSQL(sqlValidado, databaseName);
        if (resultado.getException() == null) {
            try {
                ResultSetMetaData metaData = resultado.getResultSet().getMetaData();
                List<String> resultadoList = new ArrayList<String>();
                while (resultado.getResultSet().next()) {
                   count = 1;
                   resultadoString = "";
                   while (count <= metaData.getColumnCount()) {
                       resultadoString += "\n" + metaData.getColumnName(count) + ": " + resultado.getResultSet().getString(count).toString();
                       count++;
                   }
                   resultadoList.add(resultadoString);
                }
                return resultadoList;
            } catch (Exception e) {
                return Arrays.asList(resultado.getException().getMessage());
            }
            
        }else{
            return Arrays.asList(resultado.getException().getMessage());
        }
    }

}