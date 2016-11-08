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

    public static String fileAsString(String path) {
        //File file = new File("./store/robots.txt");
        File file = new File(path);

        FileInputStream fis = null;
        String str = "";

        try {
            fis = new FileInputStream(file);
            int content;
            while ((content = fis.read()) != -1) {
                // convert to char and display it
                str += (char) content;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return str;
        }
    }

}
