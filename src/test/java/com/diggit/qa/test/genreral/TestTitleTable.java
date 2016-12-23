package com.diggit.qa.test.genreral;


import com.diggit.qa.common.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by yoosufm on 10/20/16.
 */
public class TestTitleTable {
    String dateStr = "";
    @BeforeClass
    public void init(){
        DateFormat df = new SimpleDateFormat("dd_MMM_yyyy");
        dateStr = df.format(new Date()).toString();
    }


    @Test
    public void testTotalInconsistentRowsOfDigitTitle(){
        String title = "Total inconsistent rows of Diggit title";
       String toList = "yoosuf@moogilu.com,jagadish@moogilu.com,shafeek@moogilu.com,yogesh@moogilu.com,rajnish@moogilu.com";
        //String toList = "yoosuf@moogilu.com";
        int actualRowCount = 1;
        actualRowCount = DatabaseVerifier.getLeftJoinCount();


        if(actualRowCount == Errors.DB_SERVER_NOT_AVAILABLE){
            String emailBody  = "Diggit Production Data Base Sever Is Not Accessible";
            EmailUtil.send(emailBody, emailBody,toList);
            Assert.fail(emailBody);
        } else if(actualRowCount == Errors.DB_NOT_AVAILABLE){
            String emailBody  = "Diggit Production Torrent Data Base Is Not Accessible";
            EmailUtil.send(emailBody, emailBody,toList);
            Assert.fail(emailBody);
        }
        else if(actualRowCount != 0){
            String emailBody  = "There are inconsistent rows available in Diggit title table.";
            EmailUtil.send(emailBody, title,toList);
            Assert.fail(emailBody);
        }
    }

    @Test
    public void testJobTable(){
        String title = "Total inconsistent rows of Diggit title";
        String toList = "yoosuf@moogilu.com,jagadish@moogilu.com,shafeek@moogilu.com,yogesh@moogilu.com,rajnish@moogilu.com";
        //String toList = "yoosuf@moogilu.com";
        int actualRowCount = 1;
        actualRowCount = DatabaseVerifier.getJobCount();


        if(actualRowCount == Errors.DB_SERVER_NOT_AVAILABLE){
            String emailBody  = "Diggit Production Data Base Sever Is Not Accessible";
            EmailUtil.send(emailBody, emailBody,toList);
            Assert.fail(emailBody);
        } else if(actualRowCount == Errors.DB_NOT_AVAILABLE){
            String emailBody  = "Diggit Production Job Central Schema Is Not Accessible";
            EmailUtil.send(emailBody, emailBody,toList);
            Assert.fail(emailBody);
        }

    }

    @Test
    public void testErrorReportTable(){
        String title = "Total inconsistent rows of Diggit title";
        String toList = "yoosuf@moogilu.com,jagadish@moogilu.com,shafeek@moogilu.com,yogesh@moogilu.com,rajnish@moogilu.com";
        //String toList = "yoosuf@moogilu.com";
        int actualRowCount = 1;
        actualRowCount = DatabaseVerifier.getErrorCount();


        if(actualRowCount == Errors.DB_SERVER_NOT_AVAILABLE){
            String emailBody  = "Diggit Production Data Base Sever Is Not Accessible";
            EmailUtil.send(emailBody, emailBody,toList);
            Assert.fail(emailBody);
        } else if(actualRowCount == Errors.DB_NOT_AVAILABLE){
            String emailBody  = "Diggit Production Management Schema (Error Report) Is Not Accessible";
            EmailUtil.send(emailBody, emailBody,toList);
            Assert.fail(emailBody);
        }

    }

}
