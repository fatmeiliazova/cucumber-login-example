package com.qa.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Parameters {

    private Logger log = LoggerFactory.getLogger(Parameters.class);

    public String getLoginMessage() {
        return Configuration.getProperty("login.message");
    }
    public String getAppUrl() {
        return Configuration.getProperty("appUrl").replace("env", getTestEnvironment());
    }
    public String getAdminUrl() {
        return Configuration.getProperty("adminUrl").replace("env", getTestEnvironment());
    }
    public String getApiUrl() {
        return Configuration.getProperty("apiUrl").replace("env", getTestEnvironment());
    }
    public String getTestEnvironment() {
        return Configuration.getProperty("env");
    }
    public String getBrowser() {
        return Configuration.getProperty("browser");
    }
    public String getRunAt() {
        return Configuration.getProperty("run.at");
    }
    public String getDeploy() {
        return Configuration.getProperty("deploy");
    }
    public String getResetDB() {
        return Configuration.getProperty("resetDB");
    }
    public String getNoAttempts() {
        return Configuration.getProperty("noAttempts");
    }
    public String getLoadFixtures() {
        return Configuration.getProperty("loadFixtures");
    }
    public String getCucumberOptions() {
        return Configuration.getProperty("cucumber.options");
    }
    public String getBuildWebApp() {
        return Configuration.getProperty("buildWebApp");
    }
    public String getBuildAdminWebApp() {
        return Configuration.getProperty("buildAdminWebApp");
    }
    public String getBuildUserAPI() {
        return Configuration.getProperty("buildUserAPI");
    }
    public String getBuildCustomerAPI() {
        return Configuration.getProperty("buildCustomerAPI");
    }
    public String getBuildInvestorAPI() {
        return Configuration.getProperty("buildInvestorAPI");
    }
    public String getSauceURL() {
        return Configuration.getProperty("sauceLabs.url")
                .replace("username", getSauceLabsUsername())
                .replace("access_key",getSauceLabsPassword());
    }
    public String getSaucePlatform() {
        return Configuration.getProperty("sauceplatform");
    }
    public String getSauceBrowser() {
        return Configuration.getProperty("saucebrowser");
    }
    public String getSauceBrowserVersion() {
        return Configuration.getProperty("saucebrowserversion");
    }
    public String getSauceAndroidVersion() {
        return Configuration.getProperty("sauceLabs.androiddevicename");
    }
    public String getJenkinsJobRunEnv() {
        return Configuration.getProperty("jenkinsjobrunenv");
    }
    public String getJenkinsUsername() {
        return Configuration.getProperty("jenkins.username");
    }
    public String getJenkinsPassword() {
        return Configuration.getProperty("jenkins.password");
    }
    public String getSegmentWriteKey() {
        return Configuration.getProperty("segmentWriteKey");
    }
    public String getSauceLabsUsername() {
        return Configuration.getProperty("sauceLabs.username");
    }
    public String getSauceLabsPassword() {
        return Configuration.getProperty("sauceLabs.password");
    }
    public String getAutomationReportsBucketName() {
        return Configuration.getProperty("automationReports.bucketName");
    }
    public String getAwsAccessKeyId() {
        return Configuration.getProperty("aws.accessKey.id");
    }
    public String getAwsSecretAccessKey() {
        return Configuration.getProperty("aws.secret.accessKey");
    }
    public String getSuperUserEmail() {
        return Configuration.getProperty(getTestEnvironment() + (".superUserEmail"));
    }
    public String getSuperUserPassword() {
        return Configuration.getProperty(getTestEnvironment() + (".superUserPassword"));
    }
    public String getUserPassword() {
        return Configuration.getProperty(getTestEnvironment() + (".userPassword"));
    }
    public String getCsmAdminPassword() {
        return Configuration.getProperty(getTestEnvironment() + (".csmAdminPassword"));
    }
    public String getServiceWorker() {
        return Configuration.getProperty(getTestEnvironment() + (".serviceWorker"));
    }
    public String getDBURL() {
        return Configuration.getProperty("mySQLdbURL");
    }
    public String getDBUser() {
        return Configuration.getProperty(getTestEnvironment());
    }
    public String getDBPassword() {
        return Configuration.getProperty(getTestEnvironment() + ("." + getTestEnvironment() + "_db.password"));
    }
    public String getDebugValue() {
        return Configuration.getProperty("debug");
    }
    public String getAPIRequestTimeout() {
        return Configuration.getProperty("apiRequest.timeout");
    }
}