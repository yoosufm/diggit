package com.diggit.qa.page.imd;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.TimeUnit;

/**
 * Created by yoosufm on 12/18/16.
 */
public class AnalyticPage {
    WebDriver driver;
    public AnalyticPage(String url, WebDriver driver){
        this.driver = driver;
        this.driver.navigate().to(url);
        this.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        this. driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        this.driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
    }

    public void login(String userName, String password, String role){
        driver.findElement(By.name("email")).sendKeys(userName);
        driver.findElement(By.name("password")).sendKeys(password);
        new Select(driver.findElement(By.name("role_id"))).selectByVisibleText(role);
        driver.findElement(By.xpath("//button[@class='btn btn-inverse btn-lg btn-login']")).click();

    }

    public void moveToAnalytic(){

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//a[@ng-click='goToAnalytics()']")).click();
    }

    public void search(String text){
        driver.findElement(By.xpath("//input[@ng-enter='search()']")).sendKeys(text);
        driver.findElement(By.xpath("//button[@class='btn btn-success']")).click();
    }
}
