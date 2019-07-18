package com.qa.test;

import com.qa.objects.investor.WebAppLoginPage;
import cucumber.api.java.en.And;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginTest extends InvestorPlatformUI {
    private static Logger LOG = LoggerFactory.getLogger(LoginTest.class);
    WebAppLoginPage webAppLoginPage = new WebAppLoginPage(driver);

    @And("^I am on the Login page$")
    public void verifyIAmOnLoginPage() {
        webAppLoginPage.verifyIAmOnLoginPage();
    }
}
