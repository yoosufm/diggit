package com.diggit.qa.helper.analyticclient;

import com.diggit.qa.pageobjects.analyticclient.HomePage;
import com.diggit.qa.pageobjects.analyticclient.TitleManagementPage;
import junit.framework.Assert;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by yoosuf on 11/6/2015.
 */
public class TitleManagementHelper {

    public static void createTitle(TitleManagementPage titleManagementPage, HomePage homePage, String name, String schedule, String movie){
        titleManagementPage.clickCreateTitle();
        HomeHelper.search(homePage, movie);
        titleManagementPage.enterTitleName(name);
        titleManagementPage.selectCrawl(schedule);
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
