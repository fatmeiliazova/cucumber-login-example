package com.qa.test;

import com.qa.objects.investor.WebAppLoginPage;
import com.qa.util.Decode;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvestorPlatformLoginTest extends InvestorPlatformUI {
    private static Logger LOG = LoggerFactory.getLogger(InvestorPlatformLoginTest.class);
    private WebAppLoginPage webAppLoginPage = new WebAppLoginPage(driver);
    private TestContext testContext;

    public InvestorPlatformLoginTest(TestContext testContext) {
        this.testContext = testContext;
    }

    @And("^I login as '(.*)'")
    public void loginAsInvestor(String username) {
        testContext.setEmail(username);
        driver.get(testParams.getAppUrl());
        LOG.info("Navigating to the Investor platform");
        webAppLoginPage.login(username, Decode.decodeText(InvestorPlatformUI.testParams.getUserPassword()));
        new WebDriverWait(driver, 40).until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[contains(., '" + "Investment account" + "')]"))));
    }

    @Given("^I login as user '(.*)' then verify text '(.*)'")
    public void loginAsInvestorAndVerifyText(String username, String text) {
        driver.get(testParams.getAppUrl());
        LOG.info("Navigating to the Investor platform");
        webAppLoginPage.login(username, Decode.decodeText(InvestorPlatformUI.testParams.getUserPassword()));
        new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[contains(., '" + text + "')]"))));
    }

}
