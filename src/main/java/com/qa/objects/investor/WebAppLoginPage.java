package com.qa.objects.investor;

import com.qa.objects.WebAppPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

public class WebAppLoginPage extends WebAppPage {
    private static Logger log = LoggerFactory.getLogger(WebAppLoginPage.class);

    @FindBy(css = "input[type=email]")
    private WebElement emailField;

    @FindBy(css = "input[type=password]")
    private WebElement passwordField;

    @FindBy(css = ".cta-button")
    private WebElement logInButton;

    @FindBy(linkText = "Register now")
    private WebElement registerLink;

    @FindBy(linkText = "Reset password")
    private WebElement resetPasswordLink;

    public WebAppLoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public WebAppLoginPage(WebDriver driver, String pageName) {
        super(driver, pageName);
        PageFactory.initElements(driver, this);
    }

    public void login(String email, String password) {
        log.info("Current page title is:[" + driver.getTitle() + "]");
        emailField.sendKeys(email);
        log.info("typed email: " + email);
        passwordField.sendKeys(password);
        log.info("typed password");
        logInButton.click();
        log.info("completed LOGIN as: " + email);
    }

    public void register() {
        log.info("Navigating to registration");
        clickOnRegisterNow();
        log.info("Registration link clicked");
    }

    public void clickOnResetPasswordLink() {
        resetPasswordLink.click();
    }

    public void clickOnRegisterNow() {
        registerLink.click();
    }

    public void verifyIAmOnLoginPage() {
        Assert.assertTrue(logInButton.findElement(By.tagName("span")).getText().equalsIgnoreCase("log in"), "The user is not on the login page, he is on " + driver.getCurrentUrl());
    }

}
