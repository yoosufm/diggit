package com.diggit.qa.test.infohash;


import com.diggit.qa.common.DatabaseVerifier;
import com.diggit.qa.common.StorageSample;
import com.diggit.qa.common.TextFileWriter;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
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
public class TestInfohashExistInBitsnoop {



    @Test
    public void testInfohashExistOnBitsnoop(){


        DateFormat df = new SimpleDateFormat("dd_MMM_yyyy");
        String dateStr = df.format(new Date()).toString();
        TextFileWriter.writeLineToFile("Info-hash,Is_Exist_On_Bitsnoop", "src/main/resources/Infohash_Bitsnoop_Verification" +dateStr + ".csv");

        List<String> infohashes = DatabaseVerifier.getInfohashs();

        for(String infohash : infohashes) {
            infohash =infohash.replace("\r","");
            JSONArray imdbData = getInfohashData(infohash);
            String status = "not found";
            if(imdbData != null){
                status = "exists";
            }

            TextFileWriter.writeLineToFile(infohash + "," + status , "src/main/resources/Infohash_Bitsnoop_Verification" + dateStr + ".csv");

        }

        try {
            File tempFile = new File("src/main/resources/Infohash_Bitsnoop_Verification" +dateStr + ".csv");
            StorageSample.uploadFile("Infohash_Bitsnoop_Verification" + dateStr, "text/csv", tempFile, "qa_results");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }


    public  static JSONArray getInfohashData(String infohash){
        JSONArray omdbData = null;
        String myString = "";
        try {
            String imdbID = "";
            HttpClient client = new DefaultHttpClient();

            HttpGet post = new HttpGet(System.getProperty("ctaf.api.ur", "http://bitsnoop.com/api/trackers.php?hash="+infohash+"&json=1"));

            HttpResponse response = null;
            response = client.execute(post);
            myString = IOUtils.toString(response.getEntity().getContent(), "UTF-8");

            if(myString.equalsIgnoreCase("\"NOTFOUND\"")){
                omdbData = null;
            }else {
                omdbData = new JSONArray(myString);
            }
        } catch (Exception e) {
            System.out.println(myString);
            e.printStackTrace();
        }
        return omdbData;
    }







}
