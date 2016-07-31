package com.diggit.qa.tests.analyticclient;


import com.diggit.qa.helper.analyticclient.HomeHelper;
import com.diggit.qa.helper.analyticclient.LoginHelper;
import com.diggit.qa.helper.analyticclient.TitleManagementHelper;
import com.diggit.qa.pageobjects.analyticclient.HomePage;
import com.diggit.qa.pageobjects.analyticclient.LoginPage;
import com.diggit.qa.pageobjects.analyticclient.TitleManagementPage;
import com.diggit.qa.tests.common.TestBase;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by yoosuf on 11/6/2015.
 */
public class LoginTest extends TestBase {
    protected LoginPage loginPage ;
    private HomePage homePage;
    private TitleManagementPage titleManagementPage;


    @BeforeClass
    public void init(){
        DRIVER.navigate().to("http://analytics.diggit.com/#/login");
        loginPage = new LoginPage(DRIVER);
        homePage = new HomePage(DRIVER);
        titleManagementPage = new TitleManagementPage(DRIVER);
    }

    @Test
     public void loginTest(){
        LoginHelper.login(loginPage, "yoosuf@moogilu.com", "hafsa2210");
    }

    @Test(dependsOnMethods = "loginTest", alwaysRun = true)
    public void searchTest() {
        HomeHelper.search(homePage, "Big");
        HomeHelper.verifySearchResult(homePage, "Big");
    }

    @Test(dependsOnMethods = "searchTest", alwaysRun = true)
    public void createTitleManagementTest() {
        HomeHelper.navigateToTitleManagementPage(homePage);
        TitleManagementHelper.createTitle(titleManagementPage, homePage, "test","Every hour", "Big");
        TitleManagementHelper.verifySearchResult(titleManagementPage, "Big");
    }



    @AfterClass
    public void cleanUp(){
//        DRIVER.quit();
    }
}
