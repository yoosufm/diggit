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

    public static void verifySearchResult(HomePage homePage, String text){
        List<WebElement> movieNameList = homePage.getMoviesNameList();
        for(int index = 0; index < movieNameList.size(); index++){
            String movieName =  movieNameList.get(index).getText();
            System.out.println(movieName);
            Assert.assertTrue("Expected text is not contain. Expected text : " + text + " Displaying movie Name : " + movieName, movieName.contains(text));
        }
    }

    public static void navigateToTitleManagementPage(HomePage homePage){
        homePage.clickTitleManagement();
    }
}
