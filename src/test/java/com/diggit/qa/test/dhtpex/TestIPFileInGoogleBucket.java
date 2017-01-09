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
import java.util.*;

/**
 * Created by yoosufm on 10/20/16.
 */
public class TestIPFileInGoogleBucket {



    @Test
    public void testIPFileAreAvailableInGoogleBucket() {
        DateFormat df = new SimpleDateFormat("dd_MM_yyyy_HH");
        String dateStr = df.format(new Date()).toString();
        String fileName = "dht-pex-ip-file-verification-" +dateStr + ".csv";
        String bucketPath = dateStr.split("_")[2] + "/" + dateStr.split("_")[1] + "/" + dateStr.split("_")[0] + "/" + dateStr.split("_")[3] + "/";

        TextFileWriter.writeLineToFile("Infohash,IP file status,File Size, Server Index", "src/main/resources/" +fileName);
        try {
            List<String> infohashes = DatabaseVerifier.getAllInfohashInJobTable();
            for (String infohash : infohashes) {
                String fileStatus = "Not Available";
                StorageObject get = null;
                BigInteger size = null;
                int serverIndex = 0;
                for(; serverIndex < 82 ; serverIndex ++) {
                    String filePath = getFilePath(infohash, String.valueOf(serverIndex));
                    try {
                        get = StorageSample.getBucket("dht-pex-prod", filePath);
                    } catch (GoogleJsonResponseException ex) {
                    }
                    if (get != null) {
                         size = get.getSize();
                        if (!(size.intValue() > 1)) {
                            fileStatus = "IP File is empty";
                        }else {
                            fileStatus = "Available";
                        }
                        break;
                    }
                }
                if(size == null){
                    TextFileWriter.writeLineToFile(infohash + "," + fileStatus + ", - ," + serverIndex  , "src/main/resources/" + fileName);

                }else {
                    TextFileWriter.writeLineToFile(infohash + "," + fileStatus + "," + size.intValue() +"," + serverIndex, "src/main/resources/" + fileName);
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


    public String  getFilePath(String infohash, String serverIndex){
        String filePath = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String date = dateFormat.format(cal.getTime());
        filePath = date +"/"+ infohash + "-"+serverIndex+".csv";

        return filePath;


    }

//    @Test
    public void testDHPPexWorking() throws IOException, GeneralSecurityException {
        DateFormat df = new SimpleDateFormat("dd_MM_yyyy");

                StorageObject get = null;

                String  filePath = "2017/01/02";
                try {
                    get = StorageSample.getBucket("dht-pex-prod", filePath);
                }catch (GoogleJsonResponseException ex){
                }
                Map<String, String> mData= get.getMetadata();



            //le tempFile = new File("src/main/resources/" +fileName);
         // StorageSample.uploadFile("dht-pex-ip-file-verification", "text/csv", tempFile, Constant.QA_BUCKET, bucketPath);


    }



}
