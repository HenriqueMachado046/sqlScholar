package br.com.sqlScholar.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


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

    public static ResultSet executeSQL(String sql, String databaseName){
        //Este método deverá retornar um ResultSet e, após isso, cada resultado será tratado no se respectivo service.
        sql = null;
        sql = "SELECT id, username FROM teacher WHERE id IS NOT NULL";
        try {
            String url = "jdbc:postgresql://localhost:5432/" + databaseName;
            Connection connection = DriverManager.getConnection(url, "postgres", "postgres");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);     
            connection.close();
            return resultSet;
        } catch (SQLException e) {
            System.out.println(sql + e.getMessage());
            return null;
        }
    }
    
}
