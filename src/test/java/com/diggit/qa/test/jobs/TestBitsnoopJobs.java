package com.diggit.qa.test.jobs;


import com.diggit.qa.common.*;
import com.google.api.services.bigquery.Bigquery;
import com.google.api.services.bigquery.model.Job;
import com.google.api.services.bigquery.model.JobReference;
import com.google.api.services.bigquery.model.TableCell;
import com.google.api.services.bigquery.model.TableRow;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
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
public class TestBitsnoopJobs {


    @Test
    public void testBitsnoopAPI() throws IOException, InterruptedException {
        DateFormat df = new SimpleDateFormat("dd_MM_yyyy");
        String dateStr = df.format(new Date()).toString();
        String fileName = "latest-infohash-collection" + dateStr + ".csv";
        TextFileWriter.writeLineToFile("Job Name, Status", "src/main/resources/" + fileName);
        String bucketPath = dateStr.split("_")[2] + "/" + dateStr.split("_")[1] + "/" + dateStr.split("_")[0] + "/";

        HttpClient client = new DefaultHttpClient();

        HttpGet post = new HttpGet(Constant.BITSNOOP_API);
        // StringEntity input = new StringEntity();
        //input.setContentType("application/json");
        // post.setEntity(input);

        HttpResponse response = null;
        response = client.execute(post);
        HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity, "UTF-8");
        System.out.println(responseString);
        if (responseString.isEmpty()) {
            TextFileWriter.writeLineToFile("Bitsnoop " + "," + "Bitsnoop API does not return latest info-hashes", "src/main/resources/" + fileName);
        }

        File tempFile = new File("src/main/resources/" + fileName);
        try {
            StorageSample.uploadFile("latest-infohash-collection", "text/csv", tempFile, Constant.QA_BUCKET, bucketPath);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

  /*  public void testJobsCollectInfohashes() throws IOException, InterruptedException {

        String[] jobs = {"bitsnoop"};

        for (String job : jobs) {
            Bigquery bigquery = BigQueryUtil.createAuthorizedClient();

            String querySql = "SELECT * FROM [diggit-1266:diggit_search.infohashes] where source like ('%"+job+"%');";
            JobReference jobId = BigQueryUtil.startQuery(bigquery, Constant.PROJECT_ID, querySql);

            // Poll for Query Results, return result output
            Job completedJob = BigQueryUtil.checkQueryResults(bigquery, Constant.PROJECT_ID, jobId);

            // Return and display the results of the Query Job
            List<TableRow> rows = BigQueryUtil.displayQueryResults(bigquery, Constant.PROJECT_ID, completedJob);
            if(rows == null){
                TextFileWriter.writeLineToFile(job + ", No data available", "src/main/resources/" + fileName);

            }else {
                for (TableRow row : rows) {
                    for (TableCell field : row.getF()) {
                        TextFileWriter.writeLineToFile(job + "," + field.getV().toString(), "src/main/resources/" + fileName);

                    }
                }
            }
        }
        File tempFile = new File("src/main/resources/" +fileName);
        try {
            StorageSample.uploadFile("bitsnoop-data-collection", "text/csv", tempFile, Constant.QA_BUCKET, bucketPath);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }*/

}
