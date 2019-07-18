package com.qa.test;

import com.qa.objects.investor.WebAppLoginPage;
import com.qa.objects.investor.WebAppResetPasswordPage;
import cucumber.api.java.en.And;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResetPasswordTest extends InvestorPlatformUI {
    private static Logger LOG = LoggerFactory.getLogger(ResetPasswordTest.class);

    private WebAppResetPasswordPage resetPasswordPage = new WebAppResetPasswordPage(driver);
    private WebAppLoginPage webAppLoginPage = new WebAppLoginPage(driver);

    @And("^I enter the email address as '(.*)'$")
    public void enterEmailAddress(String email) {
        resetPasswordPage.enterEmail(email);
    }


    @And("^I click on the reset password link")
    public void clickOnResetPasswordLink() {
        webAppLoginPage.clickOnResetPasswordLink();
    }


    @And("I click on the continue button")
    public void clickOnContinueButton() {
        resetPasswordPage.clickOnContinueButton();
    }
}
