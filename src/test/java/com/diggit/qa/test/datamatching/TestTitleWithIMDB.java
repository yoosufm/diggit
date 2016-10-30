package com.diggit.qa.test.datamatching;


import com.diggit.qa.common.DatabaseVerifier;
import com.diggit.qa.helper.imdb.IMDBContent;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by yoosufm on 10/20/16.
 */
public class TestTitleWithIMDB {


    @AfterMethod
    public void cleanUpTest(){
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println(" *************************** TESTING **************************** " );
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
    }



    @Test
    public void testGenres1(){
        String imdbId = "";
        String titleId =  "823709";
        List<Map<String, String>> titles = DatabaseVerifier.getTitles(titleId);
        titleId = titles.get(0).get("diggit_title_id");
        imdbId = titles.get(0).get("imdb_id");
        List<String> genresDB = DatabaseVerifier.getGenres(titleId);
        IMDBContent.navigateIMDBContentPage(imdbId);
        List<String> imdbGenres = IMDBContent.getGenres();
        System.out.println("Title ID : " + titleId  );
        System.out.println("IMDB ID : " + imdbId );

        for(String genre: imdbGenres){
            System.out.println("IMDB Genre : " + genre);
        }

        System.out.println();
        System.out.println("----------------------------------");
        System.out.println();

        if(genresDB.size() > 0) {
            for(String genre: genresDB){
                System.out.println("Diggit Genre : " + genre);
            }
        }else if(genresDB.size() == 0){
            System.out.println("No genre available in Diggit DB. IMDB Id : " + imdbId + "." +
                    " Diggit Title ID : " +  titleId );
        }


    }

    @Test
    public void testGenres2(){
        String imdbId = "";
        String titleId = "821251";
        List<Map<String, String>> titles = DatabaseVerifier.getTitles(titleId);
        titleId = titles.get(0).get("diggit_title_id");
        imdbId = titles.get(0).get("imdb_id");
        List<String> genresDB = DatabaseVerifier.getGenres(titleId);
        IMDBContent.navigateIMDBContentPage(imdbId);
        List<String> imdbGenres = IMDBContent.getGenres();
        System.out.println("Title ID : " + titleId  );
        System.out.println("IMDB ID : " + imdbId );

        for(String genre: imdbGenres){
            System.out.println("IMDB Genre : " + genre);
        }

        System.out.println();
        System.out.println("----------------------------------");
        System.out.println();

        if(genresDB.size() > 0) {
            for(String genre: genresDB){
                System.out.println("Diggit Genre : " + genre);
            }
        }else if(genresDB.size() == 0){
            System.out.println("No genre available in Diggit DB. IMDB Id : " + imdbId + "." +
                    " Diggit Title ID : " +  titleId );
        }
    }


    @Test
    public void testGenres3(){
        String imdbId = "";
        String titleId = "";
        List<Map<String, String>> titles = DatabaseVerifier.getTitles();
        titleId = titles.get(0).get("diggit_title_id");
        imdbId = titles.get(0).get("imdb_id");
        List<String> genresDB = DatabaseVerifier.getGenres(titleId);
        IMDBContent.navigateIMDBContentPage(imdbId);
        List<String> imdbGenres = IMDBContent.getGenres();
        System.out.println("Title ID : " + titleId  );
        System.out.println("IMDB ID : " + imdbId );


        for(String genre: imdbGenres){
            System.out.println("IMDB Genre : " + genre);
        }

        System.out.println();
        System.out.println("----------------------------------");
        System.out.println();

        if(genresDB.size() > 0) {
            System.out.println(genresDB.get(0));
        }else if(genresDB.size() == 0){
            System.out.println("No genre available in Diggit DB. IMDB Id : " + imdbId + "." +
                    " Diggit Title ID : " +  titleId );
        }
    }
}
