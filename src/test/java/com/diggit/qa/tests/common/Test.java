package com.diggit.qa.tests.common;

import com.diggit.qa.common.DatabaseVerifier;


/**
 * Created by Azeez on 26/09/2016.
 */
public class Test {

    public static void main(String[] args){
        DatabaseVerifier.verifyDiggitTitleIdColumnNotNullOrZero();
    }
}
