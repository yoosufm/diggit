package com.diggit.qa.test.analytics;

import com.diggit.qa.common.Constant;
import com.diggit.qa.common.EmailUtil;
import com.diggit.qa.common.Errors;
import com.diggit.qa.page.imd.AnalyticPage;
import com.diggit.qa.util.TestBase;
import com.google.gson.JsonObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Date;

/**
 * Created by yoosufm on 12/18/16.
 */
public class TestAnalytics {
    protected AnalyticPage analyticPage;


    @BeforeClass
    public void init(){
    }

    @Test
    public void testLoginAnalytic(){
        JsonObject jsonReport = new JsonObject();

        jsonReport.addProperty("email", "yoosuf@moogilu.com");
        jsonReport.addProperty("password", "hafsa2210");
        jsonReport.addProperty("role_id", "1");


        try {
            HttpClient client = new DefaultHttpClient();

            HttpGet post = new HttpGet(Constant.MANAGEMENT_HOME);
            System.out.println(jsonReport);

            HttpResponse response = null;
            response = client.execute(post);

            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity, "UTF-8");
            String status =  response.getStatusLine().getReasonPhrase();
        }catch (Exception e){
            String emailBody  = "Production Analytic Is Not Accessible";
            EmailUtil.send(emailBody, emailBody, Constant.TO_LIST);
            Assert.fail(emailBody);
        }
    }


}
