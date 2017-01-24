package com.diggit.qa.test.url;


import com.diggit.qa.common.Constant;
import com.diggit.qa.common.StorageSample;
import com.diggit.qa.common.TextFileWriter;
import com.google.gson.JsonObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yoosufm on 10/20/16.
 */
public class TestURL {
    long testSuiteStarted = 0;
    long testSuiteDuration = 0;
    DateFormat df = new SimpleDateFormat("dd_MM_yyyy");
    String dateStr = df.format(new Date()).toString();
    String fileName = "url-verification-testing" + dateStr + ".csv";
    String bucketPath = dateStr.split("_")[2] + "/" + dateStr.split("_")[1] + "/" + dateStr.split("_")[0] + "/";
    String status = "";
    @BeforeClass
    public void init(){


        TextFileWriter.writeLineToFile("URL, Status, Response Time (ms)", "src/main/resources/" + fileName);
    }



    @Test
    public void testLoadManagement() throws IOException, InterruptedException {

        JsonObject jsonReport = new JsonObject();

        jsonReport.addProperty("email", "yoosuf@moogilu.com");
        jsonReport.addProperty("password", "hafsa2210");
        jsonReport.addProperty("role_id", "1");


        try {
            HttpClient client = new DefaultHttpClient();

            HttpGet post = new HttpGet(Constant.MANAGEMENT_HOME);
            System.out.println(jsonReport);

            HttpResponse response = null;
            testSuiteStarted = new Date().getTime();
            response = client.execute(post);
            testSuiteDuration = new Date().getTime() - testSuiteStarted;

            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity, "UTF-8");
            status =  response.getStatusLine().getReasonPhrase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        TextFileWriter.writeLineToFile(Constant.MANAGEMENT_HOME + ","+status+ "," +testSuiteDuration+ "", "src/main/resources/" + fileName);


    }

    @Test
    public void testLoginToManagement() throws IOException, InterruptedException {

        JsonObject jsonReport = new JsonObject();

        jsonReport.addProperty("email", "yoosuf@moogilu.com");
        jsonReport.addProperty("password", "hafsa2210");
        jsonReport.addProperty("role_id", "1");


            try {
                HttpClient client = new DefaultHttpClient();

                HttpPost post = new HttpPost(Constant.MANAGEMENT_LOGIN);
                System.out.println(jsonReport);
                StringEntity input = new StringEntity(jsonReport.toString());
                input.setContentType("application/json");
                post.setEntity(input);

                HttpResponse response = null;
                testSuiteStarted = new Date().getTime();
                response = client.execute(post);
                testSuiteDuration = new Date().getTime() - testSuiteStarted;
                status =  response.getStatusLine().getReasonPhrase();

            } catch (Exception e) {
                e.printStackTrace();
            }

        TextFileWriter.writeLineToFile(Constant.MANAGEMENT_LOGIN + ","+status+ "," +testSuiteDuration+ "", "src/main/resources/" + fileName);

    }

    @AfterClass
    public void clean(){
        File tempFile = new File("src/main/resources/" +fileName);
        try {
            StorageSample.uploadFile("url-verification-testing", "text/csv", tempFile, Constant.QA_BUCKET, bucketPath);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
