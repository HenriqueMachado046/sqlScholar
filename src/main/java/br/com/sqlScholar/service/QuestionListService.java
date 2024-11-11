package br.com.sqlScholar.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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

    // public void criaDump(UUID questionListID, String sql) {
    //     // bug
           // O bug provavelmente advém do path estar não ser relativo, mas sim absoluto. Pensar em uma maneira melhor de usar isto. 
    //     try (BufferedWriter writer = new BufferedWriter(
    //             new FileWriter("dump" + questionListID.toString().replace("-", "") + ".sql"))) {
    //         String sql1 = "CREATE database list" + questionListID.toString().replace("-", "") + "; \\c list"
    //                 + questionListID.toString().replace("-", "") + "; ";
    //         sql1 += sql.trim();
    //         writer.write(sql1);
    //         writer.close();
    //         this.rodeSQL(
    //                 "\\i /home/iapereira/git/sqlScholar/dump" + questionListID.toString().replace("-", "") + ".sql");
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

    public void rodeSQL(String sql) {
        //Movido para sqlUtils.
        sqlUtils.createDatabase(sql);
    }

    public String rodeSQL(String sql, String database_name) throws SQLException{
         ResultSet resultSet = sqlUtils.executeSQL(sql, database_name);
         String resultado = "";
         while (resultSet.next()) {
            resultado = "ID: " + resultSet.getString(1).toString();
            resultado += "\n Usuario:" + resultSet.getString(2).toString();
         }
         return resultado;
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
