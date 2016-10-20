package com.diggit.qa.helper.imdb;

import com.diggit.qa.page.imd.IMDBContentPage;

import java.util.List;

/**
 * Created by yoosufm on 10/20/16.
 */
public class IMDBContent {
    private static IMDBContentPage imdbContentPage = new IMDBContentPage();

    public static void navigateIMDBContentPage(String imdbID){
        imdbContentPage.navigateToIMDBContentPage(imdbID);
    }

    public static List<String> getGenres(){
        return imdbContentPage.getGenres();
    }
}
