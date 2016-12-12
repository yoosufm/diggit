package com.diggit.qa.test.datamatching;

import com.diggit.qa.common.DatabaseVerifier;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Azeez on 26/09/2016.
 */
public class Test {

    public static void main(String[] args){
        try {
            DateFormat df = new SimpleDateFormat("dd_MMM_yyyy_hh");
            String dateStr = df.format(new Date()).toString();
            System.out.println(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
