package br.com.sqlScholar.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public String rodeSQL(String sql, String database_name){
        int count = 1; 
        ResultSet resultSet = sqlUtils.executeSQL(sql, database_name);
        try {
         ResultSetMetaData metaData = resultSet.getMetaData(); 
         System.out.println("QUANTIDADE DE COLUNAS QUE VIERAM DA QUERY:" + metaData.getColumnCount());
         String resultado = "";
         //Array para manter todas as informações...
         //Funciona! É importante notar que nos métodos do result set que acessam as rows pelo índice, a contagem começa em 1 E NÃO EM 0.
         List<String> resultadoList = new ArrayList<String>();
         while (resultSet.next()) {
            count = 1;
            resultado = "";
            while (count <= metaData.getColumnCount()) {
                resultado += "\n" + metaData.getColumnName(count) + " : " + resultSet.getString(count).toString();
                count++;
            }
            resultadoList.add(resultado);
         }

         for (int i = 0; i < resultadoList.size(); i++) {
            System.out.println(resultadoList.get(i));
         }

         return resultado;
            
        } catch (SQLException e) {
            //Assim, é só fazer um modal ou um alert que mostre o erro e já está concluido o "teste" do SQL.
            return e.toString();
        }
         
    }

    public void rodeSQLMultiplaInstrucoes(ArrayList<String> vetSQL, String database_name) {
       //Será movido para sqlUtils.
    }

}
