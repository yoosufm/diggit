package com.diggit.qa.tests.analyticclient;

import com.diggit.qa.common.EmailUtil;
import com.diggit.qa.helper.managementportal.LoginHelper;
import com.diggit.qa.pageobjects.managementportal.LoginPage;
import com.diggit.qa.tests.common.TestBase;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by yoosuf on 11/6/2015.
 */
public class InfohashCompare extends TestBase {
    protected LoginPage loginPage ;
    String searchText = System.getProperty("search.text", "Cabin Fever 2016");
    String diggitCount = "";
    String btnCount = "";


    @BeforeClass
    public void init(){
        loginPage = new LoginPage(DRIVER);
    }

    @Test
    public void loginTest(){
        LoginHelper.login(
                loginPage, "yoosuf@moogilu.com", "hafsa2210", "Super Admin");
        loginPage.moveToAnalytics();
        loginPage.moveToGroupManagement();
        loginPage.createNewGroup();
        loginPage.enterSearchText(searchText);
        loginPage.search();
        diggitCount = loginPage.getInfohashCount();
        DRIVER.navigate().to("https://bitsnoop.com/");
        loginPage.enterBitsnoopSearchText(searchText);
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        btnCount = loginPage.getBNSearchCount();
       // Assert.assertEquals(diggitCount, btnCount);
        String text =   "Searched text            : " + searchText + "\n"
                      + "Diggit Result Count      : " + diggitCount + "\n"
                      + "Bitsnoop Result Count    : " + btnCount.replace(")","") + "\n" ;
//        EmailUtil.send(text);


    }


    @AfterClass
    public void cleanUp(){
        //DRIVER.quit();
    }
}
