package com.diggit.qa.tests.managementportal;

import com.diggit.qa.helper.managementportal.LoginHelper;
import com.diggit.qa.pageobjects.managementportal.LoginPage;
import com.diggit.qa.tests.common.TestBase;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.UUID;

/**
 * Created by yoosuf on 11/6/2015.
 */
public class LoginTest extends TestBase {
    protected LoginPage loginPage ;



    @BeforeClass
    public void init(){
        loginPage = new LoginPage(DRIVER);
    }

    @Test
     public void loginTest(){


            LoginHelper.login(
                    loginPage, "yoosuf@moogilu.com", "hafsa2210", "Super Admin");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            loginPage.clickIngestionLink();
        for(int i = 0; i < 1; i++) {
            loginPage.clickUploadAndIngestAdsLink();
            loginPage.clickBrowse();

            setClipboardData("C:\\Users\\yoosuf\\Downloads\\20051210-w50s_56K");
            Robot robot = null;
            try {
                robot = new Robot();
            } catch (AWTException e) {
                e.printStackTrace();
            }
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);


            loginPage.clickUpload();
            loginPage.clickAddSettings();
            loginPage.clickPreRoll();;
            loginPage.clickAddRoll();
            loginPage.enterPropertyName("test_prop" + UUID.randomUUID().toString().split("-")[0]);
            loginPage.enterUrl("http://stg.ingestion.diggit.com/upload");
            loginPage.clickUpdateCuePoints();

            loginPage.clickDelete();
            System.out.println("iterations " + i);



            //DRIVER.navigate().to("https://bitsnoop.com/");
            // loginPage.enterBitsnoopSearchText(searchText);
            // Robot robot = null;
            // try {
            //       robot = new Robot();
            //   } catch (AWTException e) {
            //      e.printStackTrace();
            //  }
            //   robot.keyPress(KeyEvent.VK_ENTER);
            //   robot.keyRelease(KeyEvent.VK_ENTER);
            //  btnCount = loginPage.getBNSearchCount();
            // Assert.assertEquals(diggitCount, btnCount);
//            String text =   "Searched text            : " + searchText + "\n"
//                    + "Diggit Result Count      : " + diggitCount + "\n"
//                    + "Bitsnoop Result Count    : " + btnCount.replace(")","") + "\n" ;
//        EmailUtil.send(text);

        }
    }

    public static void setClipboardData(String string) {
        StringSelection stringSelection = new StringSelection(string);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
    }
    @AfterClass
    public void cleanUp(){
        DRIVER.quit();
    }
}
