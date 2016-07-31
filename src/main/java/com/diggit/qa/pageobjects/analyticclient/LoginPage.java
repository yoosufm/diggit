package com.diggit.qa.pageobjects.analyticclient;

import com.diggit.qa.common.SeleniumBase;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by yoosuf on 11/4/2015.
 */
public class LoginPage extends SeleniumBase {

    public WebElement txtUsername;

    public WebElement txtPassword;
    public WebElement btnLogin;

    public WebElement lnkIngestion;


    public LoginPage(WebDriver driver){
        DRIVER = driver;
    }

    public void enterUsername(String username){
        txtUsername = DRIVER.findElement(By.id("username"));
        this.txtUsername.sendKeys(username);
    }

    public void enterPassword(String password){
        txtPassword = DRIVER.findElement(By.id("password"));
        this.txtPassword.sendKeys(password);
    }

    public void clickLogin(){
        btnLogin = DRIVER.findElement(By.xpath("//button[@ng-click='login()']"));
        this.btnLogin.click();
    }





}
