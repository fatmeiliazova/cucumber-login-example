package com.qa.test;

import com.qa.util.JenkinsJobRunner;
import com.qa.util.Parameters;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.PickleEventWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;

@CucumberOptions(
        plugin = {"json:build/cucumber.json"},
        format = {
                "pretty",
                "html:target/cucumber-reports/cucumber-pretty",
                "json:target/cucumber-reports/CucumberTestReport.json",
                "rerun:target/cucumber-reports/rerun.txt"
        },
        features = {"src/test/features"},
        tags = {"@investor-regression"},
        glue = {"com.qa.test"}
)

public class TestRunner {
    private static Logger log = LoggerFactory.getLogger(TestRunner.class);
    private TestNGCucumberRunner testNGCucumberRunner;

    @BeforeSuite
    public void beforeSuite() {
        InvestorPlatformUI.testParams = new Parameters();

        String env = InvestorPlatformUI.testParams.getTestEnvironment();

        log.info(">>> Auto E2E Functional Regression Test started");
        log.info("run at: " + InvestorPlatformUI.testParams.getRunAt());
        log.info("env: " + InvestorPlatformUI.testParams.getTestEnvironment());
        log.info("browser:" + InvestorPlatformUI.testParams.getBrowser());
        log.info("options: " + InvestorPlatformUI.testParams.getCucumberOptions());
        log.info("buildCustomerAPI=" + InvestorPlatformUI.testParams.getBuildCustomerAPI());
        log.info("buildInvestorAPI=" + InvestorPlatformUI.testParams.getBuildInvestorAPI());
        log.info("buildUserAPI=" + InvestorPlatformUI.testParams.getBuildUserAPI());
        log.info("buildWebApp=" + InvestorPlatformUI.testParams.getBuildWebApp());
        log.info("buildAdminWebApp=" + InvestorPlatformUI.testParams.getBuildAdminWebApp());

        if (InvestorPlatformUI.testParams.getDeploy().equals("true")) {
            JenkinsJobRunner.run(env, JenkinsJobRunner.Job.DEPLOY_USER_API, InvestorPlatformUI.testParams.getBuildUserAPI());
            JenkinsJobRunner.run(env, JenkinsJobRunner.Job.DEPLOY_CUSTOMER_API, InvestorPlatformUI.testParams.getBuildCustomerAPI());
            JenkinsJobRunner.run(env, JenkinsJobRunner.Job.DEPLOY_INVESTOR_API, InvestorPlatformUI.testParams.getBuildInvestorAPI());
            JenkinsJobRunner.run(env, JenkinsJobRunner.Job.DEPLOY_WEBAPP, InvestorPlatformUI.testParams.getBuildWebApp());
            JenkinsJobRunner.run(env, JenkinsJobRunner.Job.DEPLOY_ADMIN_WEBAPP, InvestorPlatformUI.testParams.getBuildAdminWebApp());
        }
        if (InvestorPlatformUI.testParams.getResetDB().equals("true")) {
            JenkinsJobRunner.run(env, JenkinsJobRunner.Job.RESET_DATABASE, null);
            JenkinsJobRunner.run(env, JenkinsJobRunner.Job.CREATE_SUPER_USER, null);
            AdminUser.createUsers();
        }
    }

    @AfterSuite
    public void afterSuite() {
    }

    @Test(groups = "auto-invest")
    public void testBeforeroup(){

    }

    @BeforeClass(alwaysRun = true)
    public void setUpClass() {
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }


    @Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "scenarios")
    public void feature(PickleEventWrapper pickleWrapper, CucumberFeatureWrapper featureWrapper) throws Throwable {
        testNGCucumberRunner.runScenario(pickleWrapper.getPickleEvent());
    }

    @DataProvider
    public Object[][] scenarios() {
        return testNGCucumberRunner.provideScenarios();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        if (testNGCucumberRunner == null) {
            return;
        }
        testNGCucumberRunner.finish();
    }
}