package com.diggit.qa.pageobjects.analyticclient;

import com.diggit.qa.common.SeleniumBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by yoosuf on 11/4/2015.
 */
public class HomePage extends SeleniumBase {

    public WebElement txtSearch;
    public WebElement btnSubmit;

    public WebElement movies;


    public HomePage(WebDriver driver){
        DRIVER = driver;
    }

    public void enterSearchText(String text){
        txtSearch = DRIVER.findElement(By.xpath("//input[@ng-enter='search()']"));
        this.txtSearch.clear();
        this.txtSearch.sendKeys(text);
    }


    public void clickSearch(){
        btnSubmit = DRIVER.findElement(By.xpath("//button[@ng-click='search()']"));
        this.btnSubmit.click();
    }

    public List<WebElement> getMovieList(){
        return DRIVER.findElements(By.xpath("//div[@ng-repeat='movie in movies']"));
    }

    public List<WebElement> getMoviesNameList(){
        return DRIVER.findElements(By.xpath("//div[@ng-repeat='movie in movies']/div[@class='thumbnail']/div[@class='caption']/h4"));
    }

    public void clickTitleManagement(){
        DRIVER.findElement(By.xpath("//a[contains(text(),'Title Management')]")).click();

    }



}
