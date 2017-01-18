package com.diggit.qa.test.dhtpex;

import com.diggit.qa.common.BigQueryUtil;
import com.diggit.qa.common.Constant;
import com.diggit.qa.common.DatabaseVerifier;
import com.diggit.qa.common.TextFileWriter;
import com.google.api.services.bigquery.Bigquery;
import com.google.api.services.bigquery.model.Job;
import com.google.api.services.bigquery.model.JobReference;
import com.google.api.services.bigquery.model.TableCell;
import com.google.api.services.bigquery.model.TableRow;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yoosufm on 1/18/17.
 */
public class TestDataCollectedForGivenInfohash {

    @Test
    public void testDataCollectedForTopInfohashes() throws IOException, InterruptedException {
        DateFormat df = new SimpleDateFormat("dd_MM_yyyy");
        String dateStr = df.format(new Date()).toString();
        String fileName = "latest-infohash-data-collection" +dateStr + ".csv";
        TextFileWriter.writeLineToFile("Info-hash,IP Count", "src/main/resources/" +fileName);
        List<String> infohasehes = DatabaseVerifier.getLatestInfohashes();

        Bigquery bigquery = BigQueryUtil. createAuthorizedClient();

      //  List<String> infohasehes = new ArrayList<>();
        infohasehes.add("13A27BDB9848A2E90514D5E6CC4E70F57E0AD11F");
        infohasehes.add("89925FB48CAE260801F35FB7175530BF6E5E055A");
        infohasehes.add("91353F45AFFBC80E9DAB1A802314FC5109175DB9");
        infohasehes.add("87A2A7CAD3F9FC59086BD2B56DDDCEDEDB3B771A");

        // Print out available datasets in the "publicdata" project to the console
      //  BigQueryUtil.listDatasets(bigquery, Constant.PROJECT_ID);

        // Start a Query Job
        for(String infohash: infohasehes) {
            String querySql = "SELECT count(*) FROM [diggit-1266:diggit_hist.Diggit_IP_US] where date > '2017-01-16' and date < '2017-01-18' and infohash = '"+infohash+"';";
            JobReference jobId = BigQueryUtil.startQuery(bigquery, Constant.PROJECT_ID, querySql);

            // Poll for Query Results, return result output
            Job completedJob = BigQueryUtil.checkQueryResults(bigquery, Constant.PROJECT_ID, jobId);

            // Return and display the results of the Query Job
            List<TableRow> rows = BigQueryUtil.displayQueryResults(bigquery, Constant.PROJECT_ID, completedJob);
            for (TableRow row : rows) {
                for (TableCell field : row.getF()) {
                    TextFileWriter.writeLineToFile(infohash + "," + field.getV().toString(), "src/main/resources/" +fileName);

                   // System.out.printf(infohash + " , "+ field.getV().toString());
                }
            }
        }
    }
}
