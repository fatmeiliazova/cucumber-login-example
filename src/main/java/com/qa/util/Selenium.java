package com.qa.util;

import com.qa.test.InvestorPlatformUI;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.qa.test.InvestorPlatformUI.driver;

public class Selenium {
    private static Logger LOG = LoggerFactory.getLogger(Selenium.class);

    public static void waitToBeClickableAndClick(By locator, WebDriver driver) {
        WebElement until = new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(locator));
        until.click();
    }

    public static void waitToBeVisibleAndClick(By locator, WebDriver driver) {
        WebElement until = new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(locator));
        until.click();
        driver.findElement(locator).click();
    }

    public static String waitToBeVisibleAndGetText(WebElement element, WebDriver driver) {
        WebElement until = new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(element));
        return until.getText();
    }


    public static void validateUrlContains(WebDriver driver, String urlString) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains(urlString),"The url is " + currentUrl + " it does not contain " + urlString);
    }


    public static void  verifyTextPresent(String text) {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[contains(., '" + text + "')]"))));
    }

    public static void verifyTextNotPresent(String text) {
        new WebDriverWait(driver, 5).until(ExpectedConditions.invisibilityOfElementLocated((By.xpath("//*[contains(., '" + text + "')]"))));
    }

    public static void scrollIntoView(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView()", element);
    }

    public static void clickOnAnElementUsingJS(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }


    public static void verifyLinkPresent(String value) {
        new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOf(driver.findElement(By.linkText(value))));
    }

    public static boolean verifyElementClickable(WebDriver driver, By locator) {
        try {
            new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public static boolean verifyElementVisible(WebDriver driver, By by) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public static boolean isElementNotPresent(WebDriver driver, By by) {
        Selenium.turnOffImplicitWaits(driver);
        boolean result;
        try {
            new WebDriverWait(driver, 3).until(ExpectedConditions.presenceOfElementLocated(by));
            result = false;
        } catch (TimeoutException e) {
            result = true;
        }
        Selenium.turnOnImplicitWaits(driver);
        return result;
    }

    private static void turnOffImplicitWaits(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }

    private static void turnOnImplicitWaits(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    public static void waitForPageToLoad(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String readyState = String.valueOf(js.executeScript("return document.readyState"));
        while (!readyState.equals("complete")) {
            LOG.info("document state ... " + readyState);
            readyState = String.valueOf(js.executeScript("return document.readyState"));
        }
        LOG.info("document state: " + readyState);
    }

    public static boolean isElementNotPresent(WebDriver driver, WebElement element) {
        Selenium.turnOffImplicitWaits(driver);
        boolean result;
        try {
            new WebDriverWait(driver, 3).until(ExpectedConditions.visibilityOf(element));
            result = false;
        } catch (TimeoutException e) {
            result = true;
        }
        Selenium.turnOnImplicitWaits(driver);
        return result;
    }

    public static WebElement getParentElement(WebDriver driver, WebElement childElement) {
        return (WebElement) ((JavascriptExecutor) driver)
                .executeScript("return arguments[0].parentNode;", childElement);
    }

    public static WebElement getParentNodeWithXPath(WebElement childElement) {
        return childElement.findElement(By.xpath(".."));
    }

    public static void waitForElementToBeVisible(WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForSpecificCountForElementList(By by, int count){
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.numberOfElementsToBe(by,count));
    }

    public static void waitForStalenessOfElement(WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.stalenessOf(element));
    }

    public static void moveToElementAndClick(WebElement element){
        Actions actions = new Actions(driver);
        actions.moveToElement(element, element.getLocation().x, element.getLocation().y).click().build().perform();
    }

}