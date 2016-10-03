package com.diggit.qa.pageobjects.managementportal;

import com.diggit.qa.common.SeleniumBase;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by yoosuf on 11/4/2015.
 */
public class LoginPage extends SeleniumBase {

    public WebElement txtUsername;

    public WebElement txtPassword;

    public WebElement drpUserRole;

    public WebElement btnLogin;

    public WebElement lnkIngestion;


    public LoginPage(WebDriver driver){
        DRIVER = driver;
    }

    public void enterUsername(String username){
        txtUsername = DRIVER.findElement(By.name("email"));
        this.txtUsername.sendKeys(username);
    }

    public void enterPassword(String password){
        txtPassword = DRIVER.findElement(By.name("password"));
        this.txtPassword.sendKeys(password);
    }

    public void selectUserRole(String userRole){
        drpUserRole = DRIVER.findElement(By.name("role_id"));
        new Select(this.drpUserRole).selectByVisibleText(userRole);
    }

    public void clickLogin(){
        btnLogin = DRIVER.findElement(By.xpath("//button[@class='btn btn-inverse btn-lg btn-login']"));
        this.btnLogin.click();
    }

    public void clickIngestionLink(){
        //wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(linkText)));
        DRIVER.findElement(By.xpath("//a[contains(text(),'Ingestion')]")).click();
        //lnkIngestion.click();
    }

    public void moveToAnalytics(){
        //wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(linkText)));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        DRIVER.findElement(By.xpath("//a[contains(text(),'Analytics')]")).click();
        //lnkIngestion.click();
    }

    public void moveToGroupManagement(){
        //wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(linkText)));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        DRIVER.findElement(By.xpath("//a[contains(text(),'Group Management')]")).click();


    }

    public String getInfohashCount(){
        try{
            return DRIVER.findElement(By.xpath("//span[@class='badge pull-left ng-binding']")).getText();
        } catch (Exception e) {
            return "0";
        }

    }

    public void
    createNewGroup(){
//        try{
//            Thread.sleep(15000);
//
//        }catch (Exception e){
//
//        }
//
//        WebElement element = DRIVER.findElement(By.xpath( "//a[contains(text(),'Create New Group')]"));
//        JavascriptExecutor executor = (JavascriptExecutor)DRIVER;
//        executor.executeScript("arguments[0].click();", element);

        DRIVER.navigate().to("http://analytics.diggit.com/#/groupmgmt");
        DRIVER.findElement(By.id("imdbTitile")).sendKeys("Group1");
        DRIVER.findElement(By.xpath("//button[contains(text(),'Search Info Hash')]")).click();



    }

    public String getBNSearchCount(){
        try {
            String [] counts = DRIVER.findElement(By.xpath("//div[@id='content']/h1/span[2]")).getText().split(" ");
            return counts[counts.length-1];
        }catch (Exception e){
            return "0";
        }
    }

    public void clickUploadAndIngestAdsLink(){
        DRIVER.findElement(By.xpath("//a[contains(text(),'Upload & Ingest Ads')]")).click();
        //lnkIngestion.click();
    }

    public void clickBrowse(){
        DRIVER.findElement(By.xpath("//input[@type='file']")).click();
        //lnkIngestion.click();
    }

    public void clickUpload(){
        DRIVER.findElement(By.id("UploadButton")).click();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void clickDelete(){
        btnLogin = DRIVER.findElement(By.xpath("//button[@class='btn btn-danger btn-sm pull-right']"));
        this.btnLogin.click();
        Alert alert = DRIVER.switchTo().alert();
        alert.accept();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void clickAddSettings(){
        DRIVER.findElement(By.xpath("//td/button[@class='btn btn-default btn-sm' and contains(text(),'Ad Settings')]")).click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }    }

    public void clickPreRoll(){
        DRIVER.findElement(By.id("AdType")).click();
        //lnkIngestion.click();
    }

    public void clickAddRoll(){
        DRIVER.findElement(By.xpath("//button[@type='submit']")).click();
        //lnkIngestion.click();
    }

    public void bitsnoopSearch(){
        DRIVER.findElement(By.xpath("//div[@title='Bitsnoop']")).click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        DRIVER.findElement(By.xpath("//input[@value='Search torrents']")).click();
        //lnkIngestion.click();
    }


    public void search(){
        DRIVER.findElement(By.xpath("//html/body/div[5]/div/div/div[2]/div[1]/span/button")).click();
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void enterPropertyName(String name){
        txtUsername = DRIVER.findElement(By.id("Name"));
        this.txtUsername.sendKeys(name);
    }

    public void enterSearchText(String name){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        DRIVER.findElement(By.xpath("//input[@ng-model='searchedFileName']")).sendKeys(name);
    }

    public void enterBitsnoopSearchText(String name){
        txtUsername = DRIVER.findElement(By.id("_q1"));
        this.txtUsername.sendKeys(name);
    }

    public void enterUrl(String url){
        txtUsername = DRIVER.findElement(By.id("AdUrl"));
        this.txtUsername.sendKeys(url);
    }

    public void clickUpdateCuePoints(){
        DRIVER.findElement(By.xpath("//button[contains(text(),'Update Cue Points')]")).click();
        Alert alert = DRIVER.switchTo().alert();
        alert.accept();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
