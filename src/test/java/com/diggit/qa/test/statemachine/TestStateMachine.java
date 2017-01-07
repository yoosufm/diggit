package com.diggit.qa.test.statemachine;


import com.diggit.qa.common.*;
import com.diggit.qa.helper.imdb.IMDBContent;
import org.junit.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
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

    String dateStr = "";
    @BeforeClass
    public void init(){
        DateFormat df = new SimpleDateFormat("dd_MM_yyyy_hh");
        dateStr = df.format(new Date()).toString();
    }

    @Test
    public void testInfohashTrackCount(){

        String bucketPath = dateStr.split("_")[2] + "/" + dateStr.split("_")[1] + "/" + dateStr.split("_")[0] + "/" + dateStr.split("_")[3] + "/";

        List<String> infohashes = DatabaseVerifier.getInfohashs();
        TextFileWriter.cleanFileContents(Constant.errorLogFileName);
        TextFileWriter.writeLineToFile("State machine verification test", "src/main/resources/State_Machine_Verification_" +dateStr + ".csv");
        TextFileWriter.writeLineToFile("Infohash,Track_Count,Group_Infohash_Count,Expected_Job_Count,Actual_Job_Count,State_Machine_Status", "src/main/resources/State_Machine_Verification_" +dateStr + ".csv");
        for(String infohash: infohashes) {
            infohash =infohash.replace("\r","");
            String stateMachineStatus = "Correct";

            List<Integer> state =  DatabaseVerifier.getStateMachineCount(infohash);
            int expectedJobCount = state.get(0) - state.get(1);
            int actualJobCount = state.get(2);
            if(expectedJobCount != actualJobCount){
                stateMachineStatus = "Incorrect";
            }
            TextFileWriter.writeLineToFile(infohash + "," + state.get(0) + "," + state.get(1) + "," + expectedJobCount + "," + actualJobCount+ "," + stateMachineStatus, "src/main/resources/State_Machine_Verification_" +dateStr + ".csv");

        }

        try {
            File tempFile = new File("src/main/resources/State_Machine_Verification_" +dateStr + ".csv");
            StorageSample.uploadFile("state-machine-verification" , "text/csv", tempFile, "qa_results", bucketPath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void testTitleTrackCount(){

        // tempFile.deleteOnExit();



    }


}
