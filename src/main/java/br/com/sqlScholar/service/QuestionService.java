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

    

    public List<String> awnserQuestion(String sql,String sql_questao ,String databaseName){
        //Limitar o aluno a fazer apenas SELECT, com algum tipo de trava.
        int count = 1;
        String resultadoString = "";

        //Deve funcionar...
        String sqlAlunoValidado = sqlUtils.validateSQL(sql);
        String sqlQuestaoValidado = sqlUtils.validateSQL(sql_questao);
        
        compareSQL(sqlAlunoValidado, sqlQuestaoValidado, databaseName);

        //Resultado resultadoQueryAluno = compareSQL(sqlAlunoValidado, sqlQuestaoValidado, databaseName);
        Resultado resultadoQueryAluno = sqlUtils.executeSQL(sqlAlunoValidado, databaseName);

        //Gabarito = resultado do SQL proposto pelo professor. Pode ser armazenado.


        if (resultadoQueryAluno.getException() == null ) {
            try {
                ResultSetMetaData metaData = resultadoQueryAluno.getResultSet().getMetaData();
                List<String> resultadoList = new ArrayList<String>();
                while (resultadoQueryAluno.getResultSet().next()) {
                   count = 1;
                   resultadoString = "";
                   while (count <= metaData.getColumnCount()) {
                       resultadoString += "\n" + metaData.getColumnName(count) + ": " + resultadoQueryAluno.getResultSet().getString(count).toString();
                       count++;
                   }
                   resultadoList.add(resultadoString);
                }
                return resultadoList;
            } catch (Exception e) {
                return Arrays.asList(resultadoQueryAluno.getException().getMessage());
            }
            
        }else{
            return Arrays.asList(resultadoQueryAluno.getException().getMessage());
        }
    }


    private Resultado compareSQL (String sql, String sql_questao, String databaseName){
        
        Resultado resultadoQueryAluno = sqlUtils.executeSQL(sql, databaseName);
        Resultado resultadoQueryQuestao = sqlUtils.executeSQL(sql_questao, databaseName);
        


        if ( (resultadoQueryAluno.getException() == null && resultadoQueryQuestao.getException() == null)) {
            Resultado resultado = new Resultado();

            try {
                ResultSetMetaData metaDataAluno = resultadoQueryAluno.getResultSet().getMetaData();
                ResultSetMetaData metaDataQuestao = resultadoQueryQuestao.getResultSet().getMetaData();
            } catch (Exception e) {
                
            }


            resultado.setResultSet(resultadoQueryAluno.getResultSet());
            return resultado;
        }else{
            Resultado resultado = new Resultado();
            resultado.setResultSet(resultadoQueryAluno.getResultSet());
            resultado.setException(resultadoQueryAluno.getException());
            return resultado;
        }

    }




}