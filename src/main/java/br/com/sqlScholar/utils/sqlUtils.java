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

    public static Resultado executeSQL(String sql, String databaseName){
        Resultado resultado = new Resultado();
        sql = sql.toUpperCase();
        try {
            String url = "jdbc:postgresql://localhost:5432/" + databaseName;
            Connection connection = DriverManager.getConnection(url, "postgres", "postgres");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);     
            connection.close();
            resultado.setResultSet(resultSet);
            return resultado;
        } catch (SQLException e) {
            resultado.setException(e);
            System.out.println(sql + e.getMessage());
            return resultado; 
        }
    }

    public static String validateSQL (String sql){
        if (sql.contains("drop database") || sql.contains("alter database") || sql.contains("create database")) {
            System.out.println("ABCD123");
            sql = " ";                  
        }
        return sql;
    }
    
}
