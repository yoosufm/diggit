package com.diggit.qa.common;

import com.diggit.qa.common.Constant;

import java.io.*;

/**
 * Created by Azeez on 26/09/2016.
 */
public class TextFileWriter {

    public static void writeLineToFileWithOutOverWrite(String line){
        try{
            File file = new File(Constant.errorLogFileName);
            if(!file.exists()){
                file.createNewFile();
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file,true));
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            bufferedWriter.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
