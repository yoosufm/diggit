package com.diggit.qa.test.genreral;


import com.diggit.qa.common.*;
import org.junit.Assert;
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
        String toList = "yoosuf@moogilu.com,mak83826@gmail.com";
        if(!DatabaseVerifier.getLeftJoinCount().equalsIgnoreCase("0")){
            String emailBody  = "There are inconsistent rows available in Diggit title table.";
            EmailUtil.send(emailBody, title,toList);
        }
    }

}
