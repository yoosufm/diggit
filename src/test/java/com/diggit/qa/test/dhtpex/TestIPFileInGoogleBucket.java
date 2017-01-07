package com.diggit.qa.test.dhtpex;


import com.diggit.qa.common.Constant;
import com.diggit.qa.common.DatabaseVerifier;
import com.diggit.qa.common.StorageSample;
import com.diggit.qa.common.TextFileWriter;
import com.diggit.qa.helper.imdb.IMDBContent;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.storage.model.StorageObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by yoosufm on 10/20/16.
 */
public class TestIPFileInGoogleBucket {



    @Test
    public void testIPFileAreAvailableInGoogleBucket() {
        DateFormat df = new SimpleDateFormat("dd_MM_yyyy");
        String dateStr = df.format(new Date()).toString();
        String fileName = "dht-pex-ip-file-verification-" +dateStr + ".csv";
        String bucketPath = dateStr.split("_")[2] + "/" + dateStr.split("_")[1] + "/" + dateStr.split("_")[0] + "/";

        TextFileWriter.writeLineToFile("Infohash,IP file status", "src/main/resources/" +fileName);
        try {
            List<String> infohashes = DatabaseVerifier.getAllInfohashInJobTable();
            for (String infohash : infohashes) {
                String fileStatus = "";
                StorageObject get = null;

              String  filePath = getFilePath(infohash);
                try {
                    get = StorageSample.getBucket("dht-pex-prod", filePath);
                }catch (GoogleJsonResponseException ex){
                    fileStatus = "Not Available";
                }
                if(fileStatus.isEmpty()) {
                    BigInteger size = get.getSize();
                    if(!(size.intValue() > 1)){
                        fileStatus = "IP File is empty";
                    }
                }
                if(!fileStatus.isEmpty()) {
                    TextFileWriter.writeLineToFile(infohash + "," + fileStatus, "src/main/resources/" + fileName);
                }

            }
                File tempFile = new File("src/main/resources/" +fileName);
                StorageSample.uploadFile("dht-pex-ip-file-verification", "text/csv", tempFile, Constant.QA_BUCKET, bucketPath);

        } catch(IOException e){
            e.printStackTrace();
        } catch(GeneralSecurityException e){
            e.printStackTrace();
        }
    }


    public String  getFilePath(String infohash){
        String filePath = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String date = dateFormat.format(cal.getTime());
        filePath = date +"/"+ infohash + "-0.csv";

        return filePath;


    }



}
