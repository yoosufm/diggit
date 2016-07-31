package com.diggit.qa.helper.managementportal;

import com.diggit.qa.pageobjects.managementportal.LoginPage;

/**
 * Created by yoosuf on 11/6/2015.
 */
public class LoginHelper {

    public static void login(LoginPage loginPage, String username, String password, String role){

        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.selectUserRole(role);
        loginPage.clickLogin();

    }
}
