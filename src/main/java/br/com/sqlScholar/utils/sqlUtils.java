package br.com.sqlScholar.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class sqlUtils {

    public static void createDatabase(String sql){
        //Esta função é EXCLUSIVAMENTE para a criação de bancos de dados para listas de questões, rodando uma instrução por vez.
        try {
            String url = "jdbc:postgresql://localhost:5432/sqlscholar";
            Connection connection = DriverManager.getConnection(url, "postgres", "postgres");
            connection.createStatement().execute(sql);
            connection.close();
        } catch (SQLException e) {
            System.out.println(sql + e.getMessage());
        }
    }

    public static void executeSQL(String sql, String databaseName){
        try {
            String url = "jdbc:postgresql://localhost:5432/" + databaseName;
            Connection connection = DriverManager.getConnection(url, "postgres", "postgres");
            connection.createStatement().execute(sql);
            connection.close();
        } catch (SQLException e) {
            System.out.println(sql + e.getMessage());
        }
    }
    
}
