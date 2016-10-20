package com.diggit.qa.test.datamatching;


import com.diggit.qa.common.DatabaseVerifier;
import com.diggit.qa.helper.imdb.IMDBContent;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by yoosufm on 10/20/16.
 */
public class TestTitleWithIMDB {


    @Test
    public void testTitleName(){
        List<Map<String, String>> titles = DatabaseVerifier.getTitles();

        List<String> genresDB = DatabaseVerifier.getGenres(titles.get(0).get("diggit_title_id"));
        System.out.println(genresDB.get(0));

        IMDBContent.navigateIMDBContentPage(titles.get(0).get("imdb_id"));
        System.out.println(IMDBContent.getGenres().get(0));

    }

}
