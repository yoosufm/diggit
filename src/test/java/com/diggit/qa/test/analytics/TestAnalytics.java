package com.diggit.qa.test.analytics;

import com.diggit.qa.common.Constant;
import com.diggit.qa.common.EmailUtil;
import com.diggit.qa.common.Errors;
import com.diggit.qa.page.imd.AnalyticPage;
import com.diggit.qa.util.TestBase;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by yoosufm on 12/18/16.
 */
public class TestAnalytics extends TestBase {
    protected AnalyticPage analyticPage;
    String toList = "yoosuf@moogilu.com,jagadish@moogilu.com,shafeek@moogilu.com,yogesh@moogilu.com,rajnish@moogilu.com,gihan@moogilu.com";
    //String toList = "yoosuf@moogilu.com";


    @BeforeClass
    public void init(){
        analyticPage = new AnalyticPage(Constant.ANALYTIC_URL, DRIVER);
    }

    @Test
    public void testLoginAnalytic(){
        try {
            analyticPage.login("yoosuf@moogilu.com", "hafsa2210", "Super Admin");
            analyticPage.moveToAnalytic();
            analyticPage.search("war");
        }catch (Exception e){
            String emailBody  = "Production Analytic Is Not Accessible";
            EmailUtil.send(emailBody, emailBody,toList);
            Assert.fail(emailBody);

        }
    }


}
