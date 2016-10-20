package com.diggit.qa.common;

import java.sql.*;

/**
 * Created by Azeez on 26/09/2016.
 */
public class DatabaseConnection {

    public static Connection getDatabaseConnection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(Constant.url,Constant.username, Constant.password);
        }catch (SQLException ex){
            return null;
        }catch (ClassNotFoundException ex1){
            return null;
        }
    }

}
