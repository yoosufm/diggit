package com.diggit.qa.helper.analyticclient;

import com.diggit.qa.pageobjects.analyticclient.LoginPage;

/**
 * Created by yoosuf on 11/6/2015.
 */
public class LoginHelper {

    public static void login(LoginPage loginPage, String username, String password){

        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

    }
}
