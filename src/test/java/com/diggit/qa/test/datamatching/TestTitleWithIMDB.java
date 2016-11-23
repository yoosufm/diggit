package com.diggit.qa.test.datamatching;


import com.diggit.qa.common.DatabaseVerifier;
import com.diggit.qa.common.StorageSample;
import com.diggit.qa.common.TextFileWriter;
import com.diggit.qa.helper.imdb.IMDBContent;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by yoosufm on 10/20/16.
 */
public class TestTitleWithIMDB {



    @Test
    public void testGenres(){
        String imdbId = "";
        String imdbGenre = "";
        String diggitGenre = "";

        DateFormat df = new SimpleDateFormat("dd_MMM_yyyy");
        String dateStr = df.format(new Date()).toString();
        TextFileWriter.writeLineToFile("Title ID,IMDB ID,IMDB Genres,Diggit Genres", "src/main/resources/Genre_Verification_" +dateStr + ".csv");

        List<Map<String, String>> titles = DatabaseVerifier.getTitle();
        String titleId = titles.get(0).get("diggit_title_id");
        imdbId = titles.get(0).get("imdb_id");
        List<String> genresDB = DatabaseVerifier.getGenres(titleId);
        IMDBContent.navigateIMDBContentPage(imdbId);
        List<String> imdbGenres = IMDBContent.getGenres();
        System.out.println("Title ID : " + titleId  );
        System.out.println("IMDB ID : " + imdbId );

        for(String genre: imdbGenres){
            imdbGenre += genre + " | ";
        }

        System.out.println();
        System.out.println("----------------------------------");
        System.out.println();

        if(genresDB.size() > 0) {
            for(String genre: genresDB){
                diggitGenre += genre + " | ";
            }
        }else if(genresDB.size() == 0){
            diggitGenre = "No genre available in Diggit DB.";
        }
        TextFileWriter.writeLineToFile(titleId + "," + imdbId + "," + imdbGenre + "," +  diggitGenre, "src/main/resources/Genre_Verification_" +dateStr + ".csv");

        IMDBContent.quiteDriver();

        try {
            File tempFile = new File("src/main/resources/Genre_Verification_" +dateStr + ".csv");
            StorageSample.uploadFile("Genre_Verification_" + dateStr, "text/csv", tempFile, "qa_results");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }




}
