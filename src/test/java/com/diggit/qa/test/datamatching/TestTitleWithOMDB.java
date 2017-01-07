package com.diggit.qa.test.datamatching;


import com.diggit.qa.common.Constant;
import com.diggit.qa.common.DatabaseVerifier;
import com.diggit.qa.common.StorageSample;
import com.diggit.qa.common.TextFileWriter;
import com.diggit.qa.helper.imdb.IMDBContent;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by yoosufm on 10/20/16.
 */
public class TestTitleWithOMDB {



    @Test
    public void testGenres(){

        DateFormat df = new SimpleDateFormat("dd_MM_yyyy");
        String dateStr = df.format(new Date()).toString();
        String fileName = "genre-verification-" +dateStr + ".csv";
        TextFileWriter.writeLineToFile("Title ID,IMDB ID,IMDB Genres,Diggit Genres", "src/main/resources/" +fileName);
        String bucketPath = dateStr.split("_")[2] + "/" + dateStr.split("_")[1] + "/" + dateStr.split("_")[0] + "/";

        List<Map<String, String>> titles = DatabaseVerifier.getTitle();

        for(Map<String, String> title : titles) {
            String imdbId = "";
            String imdbGenre = "";
            String diggitGenre = "";
            String titleId = title.get("diggit_title_id");
            imdbId = title.get("imdb_id");
            List<String> genresDB = DatabaseVerifier.getGenres(titleId);

            JSONObject imdbData = getOMDBData(imdbId);
            List<String> imdbGenres = getOMDBGeneres(imdbData);

            System.out.println("Title ID : " + titleId);
            System.out.println("IMDB ID : " + imdbId);

            if(imdbGenres != null) {
                for (String genre : imdbGenres) {
                    if (imdbGenre.isEmpty()) {
                        imdbGenre = genre;
                    } else {
                        imdbGenre += " | " + genre;
                    }
                }
            }else {
                imdbGenre = "No genre available in OMDB";
            }

            if (genresDB.size() > 0) {
                for (String genre : genresDB) {
                    if (diggitGenre.isEmpty()) {
                        diggitGenre = genre;
                    } else {
                        diggitGenre += " | " + genre;
                    }
                }
            } else if (genresDB.size() == 0) {
                diggitGenre = "No genre available in Diggit DB.";
            }

            TextFileWriter.writeLineToFile(titleId + "," + imdbId + "," + imdbGenre + "," + diggitGenre, "src/main/resources/" +fileName);

        }

        try {
            File tempFile = new File("src/main/resources/" +fileName);
            StorageSample.uploadFile("genre-verification", "text/csv", tempFile, Constant.QA_BUCKET, bucketPath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }


    public  static JSONObject getOMDBData(String imdbId){
        JSONObject omdbData = null;
        try {
            String imdbID = "";
            HttpClient client = new DefaultHttpClient();

            HttpPost post = new HttpPost(System.getProperty("ctaf.api.ur", "http://www.omdbapi.com/?i="+imdbId+"&r=json"));

            HttpResponse response = null;
            response = client.execute(post);
            String myString = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
            omdbData = new JSONObject(myString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return omdbData;
    }

    public static List<String> getOMDBGeneres(JSONObject omdbData){
        List<String> omdbGenere = null;
        try {
            String genre =  omdbData.get("Genre").toString();
            if(!genre.isEmpty()) {
                omdbGenere = new ArrayList<String>(Arrays.asList(genre.split(",")));
            }
        } catch (JSONException e) {
            System.out.println(omdbData);
            e.printStackTrace();
        }

        return omdbGenere;
    }



}
