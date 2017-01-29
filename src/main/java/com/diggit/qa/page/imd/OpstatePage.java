package com.diggit.qa.page.imd;

import com.diggit.qa.common.Constant;
import com.sun.jna.platform.win32.WinBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * Created by yoosufm on 12/18/16.
 */
public class OpstatePage {
    WebDriver driver;
    By home = By.xpath("//a[@class='list-group-item']/p[contains(text(),'Home')]");
    By dhtPexYieldStat = By.xpath("//a[@class='list-group-item']/p[contains(text(),'DHT-PEX Yield Statistics')]");
    By titleStatistics = By.xpath("//a[@class='list-group-item']/p[contains(text(),'Title Statistics')]");
    By infohashState  = By.xpath("//a[@class='list-group-item']/p[contains(text(),'Infohash Statistics')]");
    By errorState = By.xpath("//a[@class='list-group-item']/p[contains(text(),'Error Statistics')]");
    By overlay = By.xpath("//div[@class=\"overlay ng-scope\" and @id=\"overlay\" and @style=\"display: block;]");

    public OpstatePage( WebDriver driver){
        this.driver = driver;
        driver.navigate().to(Constant.OP_STATE);

        this.driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        this. driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        this.driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
    }

    public void waitForOpstatHomePageLoaded(){
        isClickable(By.xpath("//div[contains(text(),'Main Statistics')]"), 30);
        //isNotClickable(overlay,30);
        //isClickable(dhtPexYieldStat, 30);
    }

    public void loadHomStat(){
        driver.findElement(home).click();
        //isNotClickable(overlay,30);
        //isClickable(home, 30);
    }

    public void loadDHTPEXStat(){
        driver.findElement(dhtPexYieldStat).click();
        //isNotClickable(overlay,30);
        isClickable(By.xpath("//div[contains(text(),'DHT-PEX Yield Statistics')]"), 30);
    }

    public void loadTitleStat(){
        driver.findElement(titleStatistics).click();
        //isNotClickable(overlay,30);
        isClickable(By.xpath("//div[contains(text(),'Title Statistics')]"), 30);
    }

    public void loadInfohashStat(){
        driver.findElement(infohashState).click();
        //isNotClickable(overlay,30);
        isClickable(By.xpath("//div[contains(text(),'Infohash Statistics')]"), 30);
    }

    public void loadErrorStat(){
        driver.findElement(errorState).click();
        isClickable(By.xpath("//div[contains(text(),'Error Statistics')]"), 30);
        //isClickable(home, 30);
    }

    public void isClickable(By by, long timeOut) {
        for(int i =0 ; i < timeOut; i++) {
            System.out.println(i);
            try {
                driver.findElement(by).click();
                break;
            } catch (Exception e) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public boolean isNotVisible(By by, long timeOut) {
        try {
            new WebDriverWait(driver, timeOut).until(ExpectedConditions.elementToBeClickable(by));
            return true;
        } catch (Exception e) {

            return false;
        }
    }


}
