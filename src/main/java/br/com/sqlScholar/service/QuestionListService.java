package br.com.sqlScholar.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.sqlScholar.model.QuestionList;
import br.com.sqlScholar.repository.QuestionListRepository;
import br.com.sqlScholar.utils.Resultado;
import br.com.sqlScholar.utils.sqlUtils;

@Service
public class QuestionListService {

    @Autowired
    QuestionListRepository questionListRepository;

    public List<QuestionList> pageableQuestionList(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<QuestionList> questionListPage = this.questionListRepository.findAll(pageable);
        return questionListPage.getContent();
    }

    public void createDatabase(String sql) {
        sqlUtils.createDatabase(sql);
    }

    public List<String> rodeSQL(String sql, String database_name){
        //Este count precisa iniciar em 1 pois o Result Set começa na posição 1 e não na posição 0.
        int count = 1;
        String resultadoString = "";
        Resultado resultado = sqlUtils.executeSQL(sql, database_name);
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

    public String getDatabaseName(){
        return null;
    }


}
