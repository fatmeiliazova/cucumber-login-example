package com.qa.util;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Element {
    private static Logger LOG = LoggerFactory.getLogger(Element.class);

    String elementName;
    String findBy;
    String pValue;

    public Element(String elementName) {
        this.elementName = elementName;
    }

    public Element(String elementName, String findBy, String pValue) {
        this.elementName = elementName;
        this.findBy = findBy;
        this.pValue = pValue;
    }

    private Element getElement(String elementName) {
        return this;
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public String getFindBy() {
        return findBy;
    }

    public void setFindBy(String findBy) {
        this.findBy = findBy;
    }

    public String getpValue() {
        return pValue;
    }

    public void setpValue(String pValue) {
        this.pValue = pValue;
    }

    public WebElement getWebElement(WebDriver driver, String elementName) {
        return getWebElement(driver, getElement(elementName));
    }

    public WebElement getWebElement(WebDriver driver, Element element) {
        switch (element.findBy) {
            case "id":
                return driver.findElement(By.id(element.pValue));
            case "name":
                return driver.findElement(By.name(element.pValue));
            case "className":
                return driver.findElement(By.className(element.pValue));
            case "xpath":
                return driver.findElement(By.xpath(element.pValue));
            case "css":
                return driver.findElement(By.cssSelector(element.pValue));
            case "linkText":
                return driver.findElement(By.linkText(element.pValue));
            case "tagName":
                return driver.findElement(By.tagName(element.pValue));
        }
        throw new RuntimeException("Please enter a valid selector");
    }

    public WebElement getWebElement(WebDriver driver) {
        return getWebElement(driver, this);
    }


    public static boolean elementExists(WebDriver driver, By by) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

}
