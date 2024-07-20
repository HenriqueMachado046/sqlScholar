package br.com.sqlScholar.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public List<QuestionList> pageableQuestionList(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<QuestionList> questionListPage = this.questionListRepository.findAll(pageable);
        return questionListPage.getContent();
    }

    public void criaDump(UUID questionListID, String sql) {
        // bug
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("dump" + questionListID.toString().replace("-", "") + ".sql"))) {
            String sql1 = "CREATE database list" + questionListID.toString().replace("-", "") + "; \\c list"
                    + questionListID.toString().replace("-", "") + "; ";
            sql1 += sql.trim();
            writer.write(sql1);
            writer.close();
            this.rodeSQL(
                    "\\i /home/iapereira/git/sqlScholar/dump" + questionListID.toString().replace("-", "") + ".sql");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void rodeSQL(String sql) {
        try {
            String url = "jdbc:postgresql://localhost:5432/sqlscholar";
            Connection conexao = DriverManager.getConnection(url, "postgres", "postgres");
            conexao.createStatement().execute(sql);
            conexao.close();
        } catch (SQLException e) {
            System.out.println("=================");
            System.out.println(sql + e.getMessage());
            System.out.println("=================");
        }
    }

    public void rodeSQL(String sql, String database_name) {
        try {
            String url = "jdbc:postgresql://localhost:5432/"+database_name;
            Connection conexao = DriverManager.getConnection(url, "postgres", "postgres");
            conexao.createStatement().execute(sql);
            conexao.close();
        } catch (SQLException e) {
            System.out.println("=================");
            System.out.println(sql + e.getMessage());
            System.out.println("=================");
        }
    }

    public void rodeSQLMultiplaInstrucoes(ArrayList<String> vetSQL) {
        try {

            String url = "jdbc:postgresql://localhost:5432/sqlscholar";
            Connection c = DriverManager.getConnection(url, "postgres", "postgres");
            c.setAutoCommit(false);
            Statement s = c.createStatement();
            for (int i = 0; i < vetSQL.size(); i++) {
                s.addBatch(vetSQL.get(i));
            }
            s.executeBatch();
            c.commit();
            s.close();
            c.close();
        } catch (SQLException e) {
            System.out.println("=================");
            System.out.println(e.getMessage());
            System.out.println("=================");
        }

    }

    public void rodeSQLMultiplaInstrucoes(ArrayList<String> vetSQL, String database_name) {
        try {
            String url = "jdbc:postgresql://localhost:5432/"+database_name;
            Connection c = DriverManager.getConnection(url, "postgres", "postgres");                        
            for (int i = 0; i < vetSQL.size(); i++) {            
                System.out.println(vetSQL.get(i));    
                c.createStatement().execute(vetSQL.get(i).trim());
            }
            c.close();
        } catch (SQLException e) {
            System.out.println("=================");
            System.out.println(e.getMessage());
            System.out.println("=================");
        }
    }

}
