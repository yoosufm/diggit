package com.diggit.qa.pageobjects.analyticclient;

import com.diggit.qa.common.SeleniumBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * Created by yoosuf on 11/4/2015.
 */
public class TitleManagementPage extends SeleniumBase {


    public TitleManagementPage(WebDriver driver){
        DRIVER = driver;
    }

    public void enterTitleName(String name){
        DRIVER.findElement(By.id("title")).sendKeys(name);
    }

    public void selectCrawl(String shedule){         ;
        new Select(this.DRIVER.findElement(By.xpath("//select[@ng-model='video.schedule']"))).selectByVisibleText(shedule);
    }


    public void clickCreateTitle(){
        DRIVER.findElement(By.xpath("//a[contains(text(),'Create New Title')]")).click();
    }

    public List<WebElement> getInfoHashNameList(){
        return DRIVER.findElements(By.xpath("//table[@ng-show='infohashes.length']/tbody/tr[@ng-repeat='row in infohashes']/td[1]"));
    }

    public void clickTitleManagement(){
        DRIVER.findElement(By.xpath("//a[contains(text(),'Title Management')]")).click();

    }



}
