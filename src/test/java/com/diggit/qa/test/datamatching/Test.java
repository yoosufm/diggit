package com.diggit.qa.test.datamatching;

import com.diggit.qa.common.DatabaseVerifier;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.Date;


/**
 * Created by Azeez on 26/09/2016.
 */
public class Test {

    public static void main(String[] args){
        try {
            String imdbID = "";
            HttpClient client = new DefaultHttpClient();

            HttpPost post = new HttpPost(System.getProperty("ctaf.api.ur", "http://www.omdbapi.com/?i=tt4172430&r=json"));

//            StringEntity input = new StringEntity();
//            input.setContentType("application/json");
//            post.setEntity(input);

            HttpResponse response = null;
            response = client.execute(post);
//            System.out.println("Complted " + featureName + "results update to Dashboard : " + new Date().toString());
            String myString = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
            System.out.println(myString);

            System.out.println(response.getStatusLine());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
