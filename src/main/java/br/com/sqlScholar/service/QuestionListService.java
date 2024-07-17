package br.com.sqlScholar.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.sqlScholar.model.QuestionList;
import br.com.sqlScholar.repository.QuestionListRepository;

@Service
public class QuestionListService {

    @Autowired
    QuestionListRepository questionListRepository;

    public List<QuestionList> pageableQuestionList(int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<QuestionList> questionListPage = this.questionListRepository.findAll(pageable);
        return questionListPage.getContent();
    } 

     public void rodeSQL(String sql){
        try {
            String url = "jdbc:postgresql://localhost:5432/sqlscholar";
            Connection conexao = DriverManager.getConnection(url, "postgres", "postgres");
            conexao.prepareStatement(sql).execute();
        } catch(SQLException e){
            System.out.println("xabum!");   
        }       
    }


}
