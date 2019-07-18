package com.qa.drivers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.test.InvestorPlatformUI;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Controls the test environment configurations
 */

public class DriverFactory {
    private Logger log = LoggerFactory.getLogger(DriverFactory.class);
    public static WebDriver driver;
    String downloadFilepath = System.getProperty("user.dir") + "/downloads";

    public WebDriver setDriver(){
        WebDriver driver = null;

        switch (InvestorPlatformUI.testParams.getRunAt()) {
            case "local":
                driver = this.setupLocalDriver(InvestorPlatformUI.testParams.getBrowser());
                break;
            case "saucelabs":
                driver = this.setupSauceLabsDriver(InvestorPlatformUI.testParams.getSaucePlatform(),
                        InvestorPlatformUI.testParams.getSauceBrowser(),
                        InvestorPlatformUI.testParams.getSauceBrowserVersion());
                break;
            default:
                log.info("Unable to setup a web driver!");
                break;
        }
        return driver;
    }

    private WebDriver createFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions()
                .addPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf")
                .addPreference("pdfjs.disabled", true)
                .addPreference("browser.download.dir", downloadFilepath)
                .addPreference("browser.download.useDownloadDir", true)
                .addPreference("browser.download.folderList", 2)
                .addPreference("pdfjs.disabled", true);
        return new FirefoxDriver(options);
    }

    private WebDriver createChromeDriver() {
        WebDriverManager.chromedriver().version("2.44").setup();
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", downloadFilepath);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);

        return new ChromeDriver(options);
    }

    private WebDriver createChromeHeadlessDriver() {
        WebDriverManager.chromedriver().version("2.44").setup();
        ChromeDriverService driverService = ChromeDriverService.createDefaultService();
        ChromeOptions chromeOptions = new ChromeOptions()
                .addArguments("--headless")
                .addArguments("--disable-gpu")
                .addArguments("--no-sandbox")
                .addArguments("--window-size=1280,1696");

        ChromeDriver driver = new ChromeDriver(driverService, chromeOptions);

        Map<String, Object> commandParams = new HashMap<>();
        commandParams.put("cmd", "Page.setDownloadBehavior");
        Map<String, String> params = new HashMap<>();
        params.put("behavior", "allow");
        params.put("downloadPath", downloadFilepath);
        commandParams.put("params", params);
        ObjectMapper objectMapper = new ObjectMapper();
        HttpClient httpClient = HttpClientBuilder.create().build();
        String command = "";
        try {
            command = objectMapper.writeValueAsString(commandParams);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String uri = driverService.getUrl().toString() + "/session/" + driver.getSessionId() + "/chromium/send_command";
        HttpPost request = new HttpPost(uri);
        request.addHeader("content-type", "application/json");
        try {
            request.setEntity(new StringEntity(command));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            httpClient.execute(request);
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return driver;
    }

    private WebDriver createIEDriver() {
        WebDriverManager.iedriver().setup();
        return new InternetExplorerDriver();
    }

    private WebDriver createEdgeDriver() {
        WebDriverManager.edgedriver().setup();
        return new EdgeDriver();
    }

    private WebDriver createSafariDriver() {
        // Safari > Preferences > Advanced tab > Show Develop menu in menu bar checkbox
        // Enable Remote Automation in the Develop menu.
        // Develop > Allow Remote Automation
        return new SafariDriver();
    }

    /**
     * Setup a local driver based on the selected choice
     */
    public WebDriver setupLocalDriver(String browserToDrive){
        WebDriver driver = null;
        switch (browserToDrive) {
            case "Firefox":
                driver = createFirefoxDriver();
                driver.manage().window().fullscreen();
                break;
            case "Chrome":
                driver = createChromeDriver();
                driver.manage().window().fullscreen();
                break;
            case "IE":
                driver = createIEDriver();
                driver.manage().window().fullscreen();
                break;
            case "Edge":
                driver = createEdgeDriver();
                driver.manage().window().fullscreen();
                break;
            case "Safari":
                driver = createSafariDriver();
                driver.manage().window().fullscreen();
                break;
            case "Chrome-Headless":
                driver = createChromeHeadlessDriver();
                break;
        }
        assert driver != null;
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        log.info("--- Local webdriver created");
        return driver;
    }

    private WebDriver setupSauceLabsDriver(String platform, String sauceBrowser, String version) {
        String URL = InvestorPlatformUI.testParams.getSauceURL();
        DesiredCapabilities caps = null;
        WebDriver driver = null;
        switch (sauceBrowser) {
            case "Chrome":
                caps = DesiredCapabilities.chrome();
                break;
            case "Firefox":
                caps = DesiredCapabilities.firefox();
                break;
            case "IE":
                caps = DesiredCapabilities.internetExplorer();
                break;
            case "Safari":
                caps = DesiredCapabilities.safari();
                break;
            case "Android":
                caps = DesiredCapabilities.android();
                caps.setCapability("deviceName", InvestorPlatformUI.testParams.getSauceAndroidVersion());
                break;
            case "Iphone":
                caps = DesiredCapabilities.iphone();
                break;
        }
        assert caps != null;
        caps.setCapability("platform", platform);
        caps.setCapability("version", version + ".0");
        caps.setCapability("name", "Investor");
        caps.setCapability("tags", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        caps.setCapability("build", System.getenv().get("build"));
        try {
            driver = new RemoteWebDriver(new URL(URL), caps);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        assert driver != null;
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        log.info("--- Saucelabs webdriver created: " + platform);
        return driver;
    }
}