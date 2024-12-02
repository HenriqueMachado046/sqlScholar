package br.com.sqlScholar.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Resultado {

    private ResultSet resultSet;
    private SQLException exception;
        
    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }
    
    public void setException(SQLException e) {
        this.exception = e;
       
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public SQLException getException() {
        return exception;
    }


}