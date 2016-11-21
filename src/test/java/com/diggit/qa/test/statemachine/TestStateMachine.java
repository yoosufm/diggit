package com.diggit.qa.test.statemachine;


import com.diggit.qa.common.Constant;
import com.diggit.qa.common.DatabaseVerifier;
import com.diggit.qa.common.EmailUtil;
import com.diggit.qa.common.TextFileWriter;
import com.diggit.qa.helper.imdb.IMDBContent;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by yoosufm on 10/20/16.
 */
public class TestStateMachine {


    @Test
    public void testInfohashTrackCount(){
        DateFormat df = new SimpleDateFormat("dd_MMM_yyyy");
        String dateStr = df.format(new Date()).toString();

        int count = Integer.valueOf(TextFileWriter.fileAsString("src/main/resources/infohash_index.txt").trim());
        int fail_count = 0;
        int success_count = 100;

        List<String> infohashes = DatabaseVerifier.getInfohashs(String.valueOf(count));
        TextFileWriter.cleanFileContents(Constant.errorLogFileName);
        TextFileWriter.writeLineToFileWithOutOverWrite("Infohash,Track_Count,Group_Infohash_Count,Expected_Job_Count,Actual_Job_Count,State_Machine_Status");
        for(String infohash: infohashes) {
            infohash =infohash.replace("\r","");
            String stateMachineStatus = "Correct";

            List<Integer> state =  DatabaseVerifier.getStateMachineCount(infohash);
            System.out.println( infohash + " info track count     " + state.get(0));
            System.out.println( infohash +  " info - group count     " +  state.get(1));
            System.out.println( infohash +  " Job count     " +  state.get(2));
            int expectedJobCount = state.get(0) - state.get(1);
            int actualJobCount = state.get(2);
            if(expectedJobCount != actualJobCount){
                stateMachineStatus = "Incorrect";
                fail_count ++;
            }
            TextFileWriter.writeLineToFileWithOutOverWrite(infohash + "," + state.get(0) + "," + state.get(1) + "," + expectedJobCount + "," + actualJobCount+ "," + stateMachineStatus, "src/main/resources/State_Machine_" +dateStr + ".csv");
            try {
                //Thread.sleep(10);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        count += 100;
        TextFileWriter.writeLineToFileWithOutOverWrite(String.valueOf(count), "src/main/resources/infohash_index.txt");
        success_count = success_count - fail_count;
        String email = EmailUtil.getStateMachineEmail(success_count, fail_count);
        EmailUtil.send(email, "State Machine Verification " + dateStr);


    }

    public static void main(String [] args){

    }


}
