package com.diggit.qa.test.url;


import com.diggit.qa.common.Constant;
import com.diggit.qa.common.EmailUtil;
import com.diggit.qa.common.StorageSample;
import com.diggit.qa.common.TextFileWriter;
import com.diggit.qa.page.imd.AnalyticPage;
import com.diggit.qa.page.imd.OpstatePage;
import com.diggit.qa.util.TestBase;
import com.google.gson.JsonObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
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
public class TestURL extends TestBase {
    protected AnalyticPage analyticPage;
    protected OpstatePage opstatePage;

    long testSuiteStarted = 0;
    long testSuiteDuration = 0;
    DateFormat df = new SimpleDateFormat("dd_MM_yyyy");
    String dateStr = df.format(new Date()).toString();
    String fileName = "url-verification-testing" + dateStr + ".csv";
    String bucketPath = dateStr.split("_")[2] + "/" + dateStr.split("_")[1] + "/" + dateStr.split("_")[0] + "/";
    String status = "";
    String description = "";

    @BeforeClass
    public void init(){

        TextFileWriter.cleanFileContents("src/main/resources/" + fileName);
        TextFileWriter.writeLineToFile("Description, URL, Status, Response Time (ms)", "src/main/resources/" + fileName);
    }


    @Test
    public void testManagementHomePage(){
        description = "Load Management Portal Login Page";
        try {
            testSuiteStarted = new Date().getTime();
            analyticPage = new AnalyticPage(Constant.MANAGEMENT_HOME, DRIVER);
            testSuiteDuration = new Date().getTime() - testSuiteStarted;
            TextFileWriter.writeLineToFile(description +","+ Constant.MANAGEMENT_HOME + ", Page is loaded," + testSuiteDuration , "src/main/resources/" + fileName);

        }catch (Exception e){
            testSuiteDuration = new Date().getTime() - testSuiteStarted;
            TextFileWriter.writeLineToFile(description +","+ Constant.MANAGEMENT_HOME + ", Page is not loaded," + testSuiteDuration , "src/main/resources/" + fileName);

        }
    }

    @Test(dependsOnMethods = "testManagementHomePage", alwaysRun = true)
    public void testLoginToManagement(){
        description = "Login to Management Portal";

        try {
            testSuiteStarted = new Date().getTime();

            analyticPage.login("yoosuf@moogilu.com", "hafsa2210", "Super Admin");

            testSuiteDuration = new Date().getTime() - testSuiteStarted;
            TextFileWriter.writeLineToFile(description +","+Constant.MANAGEMENT_LOGIN + ", Page is loaded," + testSuiteDuration , "src/main/resources/" + fileName);

        }catch (Exception e){
            testSuiteDuration = new Date().getTime() - testSuiteStarted;
            TextFileWriter.writeLineToFile(description +","+Constant.MANAGEMENT_LOGIN + ", Page is not loaded," + testSuiteDuration , "src/main/resources/" + fileName);
        }
    }

    @Test(dependsOnMethods = "testLoginToManagement", alwaysRun = true)
    public void testAnalytic(){
        description = "Navigate to Analytic Page";
        sleep(10000);
        try {
            testSuiteStarted = new Date().getTime();
            analyticPage.moveToAnalytic();
            analyticPage.search("war");
            testSuiteDuration = new Date().getTime() - testSuiteStarted;
            TextFileWriter.writeLineToFile(description +","+Constant.ANALYTIC_URL + ", Page is loaded," + testSuiteDuration , "src/main/resources/" + fileName);

        }catch (Exception e){
            testSuiteDuration = new Date().getTime() - testSuiteStarted;
            TextFileWriter.writeLineToFile(description +","+Constant.ANALYTIC_URL + ", Page is not loaded," + testSuiteDuration , "src/main/resources/" + fileName);
        }
    }

    @Test(dependsOnMethods = "testLoginToManagement", alwaysRun = true)
    public void testNavigateOpstatHomePage(){
        description = "Navigate to Opstats Home Page";
        opstatePage = new OpstatePage(DRIVER);
        try {
            sleep(10000);
            testSuiteStarted = new Date().getTime();
            analyticPage.moveToOpstate();
            opstatePage.waitForOpstatHomePageLoaded();
            testSuiteDuration = new Date().getTime() - testSuiteStarted;
            TextFileWriter.writeLineToFile(description +","+Constant.OP_STATE + ", Page is loaded," + testSuiteDuration , "src/main/resources/" + fileName);

        }catch (Exception e){
            e.printStackTrace();
            testSuiteDuration = new Date().getTime() - testSuiteStarted;
            TextFileWriter.writeLineToFile(description +","+Constant.OP_STATE + ", Page is not loaded," + testSuiteDuration , "src/main/resources/" + fileName);
        }
    }

    @Test(dependsOnMethods = "testNavigateOpstatHomePage", alwaysRun = true)
    public void testNavigateOpstatDHTPEXYieldStatisticsPage(){
        description = "Navigate to DHT-PEX Yield Statistics Page";
        sleep(10000);

        try {
            testSuiteStarted = new Date().getTime();
            opstatePage.loadDHTPEXStat();
            testSuiteDuration = new Date().getTime() - testSuiteStarted;
            TextFileWriter.writeLineToFile(description +","+Constant.OP_STATE + ", Page is loaded," + testSuiteDuration , "src/main/resources/" + fileName);

        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();
            testSuiteDuration = new Date().getTime() - testSuiteStarted;
            TextFileWriter.writeLineToFile(description +","+Constant.OP_STATE + ", Page is not loaded," + testSuiteDuration , "src/main/resources/" + fileName);
        }
    }

    @Test(dependsOnMethods = "testNavigateOpstatDHTPEXYieldStatisticsPage", alwaysRun = true)
    public void testNavigateOpstatTitleStatisticsPage(){
        description = "Navigate to Title Statistics Page";
        sleep(10000);

        try {
            testSuiteStarted = new Date().getTime();
            opstatePage.loadTitleStat();
            testSuiteDuration = new Date().getTime() - testSuiteStarted;
            TextFileWriter.writeLineToFile(description +","+Constant.OP_STATE + ", Page is loaded," + testSuiteDuration , "src/main/resources/" + fileName);

        }catch (Exception e){
            e.printStackTrace();
            testSuiteDuration = new Date().getTime() - testSuiteStarted;
            TextFileWriter.writeLineToFile(description +","+Constant.OP_STATE + ", Page is not loaded," + testSuiteDuration , "src/main/resources/" + fileName);
        }
    }

    @Test(dependsOnMethods = "testNavigateOpstatTitleStatisticsPage", alwaysRun = true)
    public void testNavigateOpstatInfoHashPage(){
        description = "Navigate to Info-Hash Statistics Page";
        sleep(10000);

        try {
            testSuiteStarted = new Date().getTime();
            opstatePage.loadInfohashStat();
            testSuiteDuration = new Date().getTime() - testSuiteStarted;
            TextFileWriter.writeLineToFile(description +","+Constant.OP_STATE + ", Page is loaded," + testSuiteDuration , "src/main/resources/" + fileName);

        }catch (Exception e){
            e.printStackTrace();
            testSuiteDuration = new Date().getTime() - testSuiteStarted;
            TextFileWriter.writeLineToFile(description +","+Constant.OP_STATE + ", Page is not loaded," + testSuiteDuration , "src/main/resources/" + fileName);
        }
    }

    @Test(dependsOnMethods = "testNavigateOpstatInfoHashPage", alwaysRun = true)
    public void testNavigateOpstatErrorPage(){
        description = "Navigate to Error Statistics Page";
        sleep(10000);

        try {
            testSuiteStarted = new Date().getTime();
            opstatePage.loadErrorStat();
            testSuiteDuration = new Date().getTime() - testSuiteStarted;
            TextFileWriter.writeLineToFile(description +","+Constant.OP_STATE + ", Page is loaded," + testSuiteDuration , "src/main/resources/" + fileName);

        }catch (Exception e){
            e.printStackTrace();
            testSuiteDuration = new Date().getTime() - testSuiteStarted;
            TextFileWriter.writeLineToFile(description +","+Constant.OP_STATE + ", Page is not loaded," + testSuiteDuration , "src/main/resources/" + fileName);
        }
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

        DRIVER.quit();
    }

}
