package com.qa.objects.investor;

import com.qa.objects.WebAppPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebAppResetPasswordPage extends WebAppPage {
    private static Logger LOG = LoggerFactory.getLogger(WebAppResetPasswordPage.class);

    @FindBy(name = "email")
    private WebElement email;

    @FindBy(css = ".cta-button")
    private WebElement continueButton;

    public WebAppResetPasswordPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }


    public void enterEmail(String emailString) {
        email.sendKeys(emailString);
    }

    public void clickOnContinueButton() {
        continueButton.click();
    }


}
