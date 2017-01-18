package com.diggit.qa.common;

/**
 * Created by Azeez on 26/09/2016.
 */
public class Constant {

//    public static String DB_URL = "jdbc:mysql://104.197.152.119:3306/torrents";
//    public static String DB_USERNAME = "readonly";
//    public static String DB_PASSWORD = "readonly@125";
    public static String DB_URL = "jdbc:mysql://104.197.169.170:3306/torrents";
    public static String DB_USERNAME = "readonly";
    public static String DB_PASSWORD = "readadmin@3345";
    //public static String errorLogFileName =  System.getProperty("user.home") +"\\StateMachine.txt";
    public static String errorLogFileName = "src/main/resources/StateMachine.csv";

    public static String IMDB_URL = "http://www.imdb.com/title/";

    public static String ALL_SUCCESS = "1. State machine is correct for all 100 info-hashes. \n2. No issues found";
    public static String SOME_FAILED = "1. State machine is correct for s_count info-hashes.  \n2. State machine is incorrect for f_count info-hashes.";

    public static boolean IS_SEND_MAIL = Boolean.valueOf(System.getProperty("is.send.mail","false"));
    public static String ANALYTIC_URL = "http://management.diggit.com/login";
    public static String TO_LIST = System.getProperty("toList","yoosuf@moogilu.com,jagadish@moogilu.com,shafeek@moogilu.com,rajnish@moogilu.com,gihan@moogilu.com,it@moogilu.com");
    public static String QA_BUCKET = "diggit-qa";
    public static String PROJECT_ID = "diggit-1266";
}
