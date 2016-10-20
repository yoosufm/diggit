package com.diggit.qa.helper.analyticclient;

import com.diggit.qa.pageobjects.analyticclient.HomePage;
import com.diggit.qa.pageobjects.analyticclient.LoginPage;
import junit.framework.Assert;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by yoosuf on 11/6/2015.
 */
public class HomeHelper {

    public static void search(HomePage homePage, String text){
        homePage.enterSearchText(text);
        homePage.clickSearch();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void navigateToTitleManagementPage(HomePage homePage){
        homePage.clickTitleManagement();
    }
}
