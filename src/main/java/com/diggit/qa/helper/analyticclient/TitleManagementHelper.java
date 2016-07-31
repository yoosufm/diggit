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

    public static void verifySearchResult(TitleManagementPage titleManagementPagee, String text){
        List<WebElement> infoHashNameList = titleManagementPagee.getInfoHashNameList();
        for(int index = 0; index < infoHashNameList.size(); index++){
            String movieName =  infoHashNameList.get(index).getText();
            System.out.println(movieName);
            Assert.assertTrue("Expected text is not contain. Expected text : " + text + " Displaying movie Name : " + movieName, movieName.contains(text));
        }
    }

    public static void navigateToTitleManagementPage(HomePage homePage){
        homePage.clickTitleManagement();
    }
}
