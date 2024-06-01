package store.model;

import java.sql.*;

public class Database {
    final private String user = "root";
    final private String password = "Msho@3830";
    final private String url = "jdbc:mysql://localhost:3306/store_schema";
    private Statement statement;

    public Database() {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Statement getStatement() {
        return statement;
    }
}
