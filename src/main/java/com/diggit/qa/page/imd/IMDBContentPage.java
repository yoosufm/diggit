package com.diggit.qa.page.imd;

import com.diggit.qa.common.Constant;
import com.diggit.qa.common.SeleniumBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yoosufm on 10/20/16.
 */
public class IMDBContentPage extends SeleniumBase {

    private By imdbGenres = By.xpath("//div[@class='title_wrapper']/div[@class='subtext']/a/span[@class='itemprop']");

    public IMDBContentPage(String imdbID) {
        super(Constant.IMDB_URL + imdbID);
    }

    public IMDBContentPage() {
    }


    public void navigateToIMDBContentPage(String imdbID) {
        try {
            navigateTo(Constant.IMDB_URL + imdbID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getGenres() {
        List<String> strGenres = new ArrayList<>();
        List<WebElement> weGenres = getElements(imdbGenres);
        for (WebElement element : weGenres) {
            strGenres.add(getText(element));
        }
        return strGenres;
    }

    public void quite(){
        driverQuite();
    }
}
