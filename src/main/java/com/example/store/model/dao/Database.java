package com.example.store.model.dao;

import java.sql.*;

public class Database {
    final private String USERNAME = "root";
    final private String PASSWORD = "Msho@3830";
    final private String DB_URL = "jdbc:mysql://localhost:3306/store_schema";
    private Statement statement;

    public Database() {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Statement getStatement() {
        return statement;
    }
}
