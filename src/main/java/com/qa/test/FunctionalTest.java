package com.qa.test;

import com.qa.drivers.DriverFactory;
import com.qa.objects.WebAppPage;
import com.qa.util.JenkinsJobRunner;
import com.qa.util.Screenshot;
import com.qa.util.Selenium;
import cucumber.api.Result;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class FunctionalTest {
    private static Logger LOG = LoggerFactory.getLogger(FunctionalTest.class);

    @Before("@fixtures")
    public void runFixtureBeforeThisTestCase() {
        JenkinsJobRunner.run(InvestorPlatformUI.testParams.getTestEnvironment(), JenkinsJobRunner.Job.LOAD_FIXTURES, null);
    }

    @Before
    public void setup(Scenario scenario) {
        InvestorPlatformUI.driver = new DriverFactory().setDriver();
        InvestorPlatformUI.scenario = scenario.getName();
        LOG.info("SCENARIO >>> " + scenario.getName());
    }

    @After
    public void finishTest(Scenario scenario) {
        if (scenario.getStatus().equals(Result.Type.PASSED)) {
            String pass = "--- Scenario ........................... PASSED";
            LOG.info(pass);
        } else {
            Screenshot.takeScreenshot(scenario);
            String fail = "--- Scenario ........................... FAILED";
            LOG.info(fail);


        }
        InvestorPlatformUI.driver.quit();
    }

    @And("^I logout of the platform")
    public void logout() {
        InvestorPlatformUI.driver.findElement(By.cssSelector("button[class*=\"expand\"]")).findElement(By.tagName("i")).click();
        WebElement avatar = InvestorPlatformUI.driver.findElement(By.cssSelector("div[class$=\"active\"]"));
        List<WebElement> elements = avatar.findElements(By.cssSelector("button[class$=\"menu__option\"]"));
        for (WebElement element : elements) {
            LOG.info(element.findElement(By.tagName("div")).getText().toLowerCase());
            if (element.findElement(By.tagName("div")).getText().toLowerCase().equals("logout")) {
                element.findElement(By.tagName("div")).click();
                break;
            }

        }
        LOG.info("investor logged out.");
    }

    @When("^User clicks the '(.*)' link")
    public void clickLink(String link) {
        InvestorPlatformUI.driver.findElement(By.linkText(link)).click();
        LOG.info("Clicked: " + link);
    }

    @Given("^I am on the platform$")
    public void platformNavigatePortal() {
        InvestorPlatformUI.driver.get(InvestorPlatformUI.testParams.getAppUrl());
    }

    @Given("^I am on the admin panel")
    public void navigateToInvestorAdmin() {
        InvestorPlatformUI.driver.get(InvestorPlatformUI.testParams.getAdminUrl());
    }

    @And("I am on the '(.*)' page$")
    public void getCurrentPage(String page) {
        Selenium.validateUrlContains(InvestorPlatformUI.driver, WebAppPage.getUrlStringForAPage(page));
        LOG.info(" I am on the page = " + page);
    }

    @Then("^Verify '(.*)' text")
    public static void verifyText(String text) {
        Selenium.verifyTextPresent(text);
    }

    @Then("^Verify '(.*)' error message")
    public static void verifyErrorMessage(String text) {
        Selenium.verifyTextPresent(text);
    }

    @Then("^I verify '(.*)' text is not present")
    public static void verifyTextNotPresent(String text) {
        Selenium.verifyTextNotPresent(text);
    }

}
