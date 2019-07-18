package com.qa.util;

import com.qa.test.InvestorPlatformUI;
import cucumber.api.Scenario;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;

public class Screenshot {
    public static void takeScreenshot(Scenario scenario) {
        ((JavascriptExecutor) InvestorPlatformUI.driver).executeScript("window.scrollTo(0, document.body.scrollHeight/3)");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ((JavascriptExecutor) InvestorPlatformUI.driver).executeScript("document.body.style.transform = \"scale(.33)\"");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        File screenshot = ((TakesScreenshot) InvestorPlatformUI.driver).getScreenshotAs(OutputType.FILE);
        byte[] data = new byte[0];
        try {
            data = FileUtils.readFileToByteArray(screenshot);
        } catch (IOException e) {
            e.printStackTrace();
        }
        scenario.embed(data, "image/png");
    }
}
