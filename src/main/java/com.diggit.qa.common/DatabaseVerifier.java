package com.diggit.qa.common;

import com.diggit.qa.common.DatabaseConnection;
import com.diggit.qa.common.TextFileWriter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Azeez on 26/09/2016.
 */
public class DatabaseVerifier {

    public static void verifyDiggitTitleIdColumnNotNullOrZero(){
        try{
            Statement statement = DatabaseConnection.getDatabaseConnection().
                    createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet = statement.executeQuery("SELECT diggit_title_id FROM mm_titles");
            while (resultSet.next()){
                long diggit_title_id = resultSet.getInt("diggit_title_id");
                if(diggit_title_id == 0){
                    TextFileWriter.writeLineToFileWithOutOverWrite(diggit_title_id + "\n");
                }else{
                    TextFileWriter.writeLineToFileWithOutOverWrite(diggit_title_id + "\n");
                }
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
