package com.diggit.qa.common;

import java.sql.*;

/**
 * Created by Azeez on 26/09/2016.
 */
public class DatabaseConnection {

    public static Connection getDatabaseConnection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(Constant.DB_URL,Constant.DB_USERNAME, Constant.DB_PASSWORD);
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }

    }
}
