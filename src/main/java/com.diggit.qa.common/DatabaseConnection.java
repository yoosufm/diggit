package com.diggit.qa.common;

import java.sql.*;

/**
 * Created by Azeez on 26/09/2016.
 */
public class DatabaseConnection {

    public static Connection getDatabaseConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = DriverManager.getConnection(Constant.DB_URL,Constant.DB_USERNAME, Constant.DB_PASSWORD);
        return connection;
    }
}
