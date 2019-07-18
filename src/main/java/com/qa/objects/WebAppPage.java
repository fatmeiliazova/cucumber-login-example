package com.qa.objects;

import com.qa.objects.investor.*;
import com.qa.util.Element;
import com.qa.util.ObjectMap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class WebAppPage {
    private static Logger LOG = LoggerFactory.getLogger(WebAppPage.class);

    public WebDriver driver;
    private ObjectMap objMap;
    public WebDriverWait wait;
    public String pageName;

    @FindBy(css = ".user-area button")
    private WebElement logOutBtn;

    @FindBy(css = ".notification-box")
    private WebElement logOutNotification;

    @FindBy(css = "p[class$=\"account-number\"]")
    private WebElement accountNumber;

    public WebAppPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        PageFactory.initElements(driver, this);
    }

    public WebAppPage(WebDriver driver, String pageName) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 10);
        objMap = new ObjectMap(pageName);
        this.pageName = pageName;
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        PageFactory.initElements(driver, this);
    }

    public void logOut() {
        this.driver.findElement(By.cssSelector("button[class*=\"expand\"]")).findElement(By.tagName("i")).click();
        WebElement avatar = this.driver.findElement(By.cssSelector("div[class$=\"active\"]"));
        List<WebElement> elements = avatar.findElements(By.cssSelector("button[class$=\"menu__option\"]"));
        for (WebElement element : elements) {
            LOG.info(element.findElement(By.tagName("div")).getText().toLowerCase());
            if (element.findElement(By.tagName("div")).getText().toLowerCase().equals("logout")) {
                element.findElement(By.tagName("div")).click();
                break;
            }

        }
        Assert.assertTrue(logOutNotification.getText().contains("logged out from your account"));
        LOG.info("Investor Logged Out.");
    }

    public String getAccountNumber() {
        driver.findElement(By.cssSelector("button[class*=\"expand\"]")).findElement(By.tagName("i")).click();
        String acctNumber = accountNumber.getText();
        LOG.info("AccountNumber=" + acctNumber);
        return acctNumber;

    }

    public void click(Element element) {
        element.getWebElement(driver, element).click();
        LOG.info("Clicked: " + element.getElementName());
    }


    public void click(WebElement w) {
        w.click();
    }

    public static WebAppPage createWebAppPageObject(String pageName, WebDriver driver) {

        switch (pageName) {
            case "Login":
                return new WebAppLoginPage(driver, pageName);
            default:
                return null;
        }

    }

    public static String getUrlStringForAPage(String pageName) {
        switch (pageName) {
            case "Account":
                return "standard";
            case "Register":
                return "register";
            case "ResetPassword":
                return "reset";
            default:
                throw new RuntimeException("The page does not exist");
        }
    }

    public void refreshCurrentPage() {
        driver.navigate().refresh();
        LOG.info("Refreshing current page");
    }

}
