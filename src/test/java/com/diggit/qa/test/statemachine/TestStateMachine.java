package com.diggit.qa.test.statemachine;


import com.diggit.qa.common.DatabaseVerifier;
import com.diggit.qa.common.TextFileWriter;
import com.diggit.qa.helper.imdb.IMDBContent;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by yoosufm on 10/20/16.
 */
public class TestStateMachine {


    @Test
    public void testInfohashTrackCount(){

        String [] infohashes = TextFileWriter.fileAsString("src/main/resources/infohashes.txt").split("\n");
        for(int i = 0 ;  i < infohashes.length ; i++) {
            System.out.println( i + " info count     " + DatabaseVerifier.getInfohashTrackCount(infohashes[i]));
            System.out.println( i +  " info - group count     " + DatabaseVerifier.getGroupInfohashCount(infohashes[i]));
            System.out.println( i +  " Job count     " + DatabaseVerifier.getJobCount(infohashes[i]));

        }

    }


}
