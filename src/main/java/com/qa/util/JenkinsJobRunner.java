package com.qa.util;

import com.qa.test.InvestorPlatformUI;
import cucumber.deps.com.thoughtworks.xstream.core.util.Base64Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import javax.json.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class JenkinsJobRunner {
    private static Logger LOG = LoggerFactory.getLogger(JenkinsJobRunner.class);
    private String responseMessage;
    private HashMap<String, String> headerFields = new HashMap<>();
    private int responseCode;
    private String job;
    private String env;
    private String options;
    private String headerLocation;
    private String basicAuth;
    private static String environment = InvestorPlatformUI.testParams.getTestEnvironment();

    private StringBuilder progressiveOutput;

    private JenkinsJobRunner() {
        String username = InvestorPlatformUI.testParams.getJenkinsUsername();
        String password = InvestorPlatformUI.testParams.getJenkinsPassword();
        String userCredentials = username + ":" + password;
        basicAuth = "Basic " + new Base64Encoder().encode(userCredentials.getBytes());
    }

    public enum Job {
        AUTO_INVEST("AUTO_INVEST"),
        LOAD_FIXTURES("LOAD_FIXTURES"),
        TRANCHES_REDEEM("TRANCHES_REDEEM"),
        DEPLOY_WEBAPP("DEPLOY_WEBAPP"),
        DEPLOY_ADMIN_WEBAPP("DEPLOY_ADMIN_WEBAPP"),
        DEPLOY_USER_API("DEPLOY_USER_API"),
        DEPLOY_CUSTOMER_API("DEPLOY_CUSTOMER_API"),
        DEPLOY_INVESTOR_API("DEPLOY_INVESTOR_API"),
        INTEREST_ARREARS_PAY("INTEREST_ARREARS_PAY"),
        GENERATE_ANNUAL_STATEMENTS("GENERATE_ANNUAL_STATEMENTS"),
        RESET_DATABASE("RESET_DATABASE"),
        CREATE_SUPER_USER("CREATE_SUPER_USER");

        private final String name;

        Job(String name) {
            this.name = name;
        }

        private String getName() {
            return this.name;
        }
    }

    public static void run(String env, Job job, String params) {
        run(env, job.getName(), params);
    }

    public static void run(String env, String job, String options) {
        try {
            JenkinsJobRunner runner = new JenkinsJobRunner();
            runner.env = env;
            runner.job = job;
            runner.options = options;
            if (job.toLowerCase().contains("deploy")) {
                runner.runDeploys(runner.env, runner.job, runner.options);
            } else {
                runner.runAPICommands(runner.env, runner.job, runner.options);
            }
            String latestBuild = runner.getLastBuildNumber(job);
            LOG.info("Latest Build: " + latestBuild);
            JsonObject jsonResponse = runner.getBuildJsonOutput(latestBuild, job);
            runner.captureBuildParameters(jsonResponse, runner.env);
            runner.validateJenkinsConsoleProgressiveText(latestBuild, job, "0");
            jsonResponse = runner.getBuildJsonOutput(latestBuild, job);
            Assert.assertTrue(runner.isBuildSuccessful(jsonResponse));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void captureBuildParameters(JsonObject buildjson, String env) {
        LOG.info("Verifying parameters ---");
        JsonArray actionsArray = buildjson.getJsonArray("actions");
        JsonArray paramsArray;
        for (int x = 0; x < actionsArray.size(); x++) {
            JsonObject innerObj = actionsArray.getJsonObject(x);
            LOG.info("Action array object: " + innerObj);
            if (innerObj.containsKey("parameters")) {
                paramsArray = innerObj.getJsonArray("parameters");
                LOG.info("Parameters array: " + paramsArray);
                for (int i = 0; i < paramsArray.size(); i++) {
                    LOG.info("value " + i + ": " + paramsArray.getJsonObject(i));
                    JsonObject o = paramsArray.getJsonObject(i);
                    if (o.getJsonString("name").toString().contains("ENV")) {
                        String v = o.getJsonString("value").toString();
                        LOG.info("ENV value is:" + v);
                        if (v.contains(env)) {
                            LOG.info("build from correct ENV was found");
                        } else {
                            LOG.info("build found does not belong to the correct environment under test");
                        }
                        break;
                    }
                }
                break;
            } else {
                LOG.info("not found yet");
            }
        }
    }

    private void runAPICommands(String env, String job, String options) throws IOException, InterruptedException {
        LOG.info("Running: " + job + " | on: " + env.toUpperCase() + "| with options: " + options);
        URL url;
        if (options != null) {
            url = new URL("url" + job + "/buildWithParameters?ENV=" + env
                    + options);
        } else {
            url = new URL("url" + job + "/buildWithParameters?ENV=" + env);
        }
        HttpURLConnection conn = makeRequestToJenkins(url, "POST");
        conn.disconnect();
        url = new URL(headerLocation + "api/json");
        getBuildNoCreatedAndJobResponse(url);
    }

    private void runDeploys(String env, String job, String version) throws IOException, InterruptedException { //VERSION
        LOG.info("Running: " + job + " | on: " + env.toUpperCase() + "| with version: " + version);
        URL url;
        version = version != null ? version : "prod.latest";
        url = new URL("url/" + job + "/buildWithParameters?ENV=" + env
                + "&VERSION=" + version);
        HttpURLConnection conn = makeRequestToJenkins(url, "POST");
        conn.disconnect();
        url = new URL(headerLocation + "api/json");
        getBuildNoCreatedAndJobResponse(url);
    }


    private void getBuildNoCreatedAndJobResponse(URL url) throws InterruptedException, IOException {
        JsonObject executable = null;
        HttpURLConnection conn;
        while (true) {
            LOG.info("trying to get build number...");
            conn = makeRequestToJenkins(url, "GET");
            JsonReader jr = Json.createReader(conn.getInputStream());
            JsonObject queue = jr.readObject();
            try {
                executable = queue.getJsonObject("executable");
            } catch (ClassCastException e) {
                conn.disconnect();
                TimeUnit.SECONDS.sleep(3);
            }
            if (executable != null) {
                executable = queue.getJsonObject("executable");
                JsonNumber number = executable.getJsonNumber("number");
                int buildNo = number.intValue();
                LOG.info("got the build:" + buildNo);
                break;
            }
            TimeUnit.SECONDS.sleep(3);
        }
        if (responseMessage.contains("Created") && responseCode == 201
                || responseCode == 200 && responseMessage.contains("OK")) {
            LOG.info("job run started...");
        } else {
            LOG.info("failed to start job or response different from expected");
        }
        conn.disconnect();
    }

    private void validateJenkinsConsoleProgressiveText(String buildno, String job, String charNoToStart) throws IOException {
        LOG.info("Getting Run Output...");
        BufferedReader br;
        String outputLine;
        String dataKey;
        URL url;
        while (true) {
            if (job.toLowerCase().contains("deploy")) {
                url = new URL("url" + job + "/" + buildno + "/logText/progressiveText?start=" + charNoToStart);
            } else {
                url = new URL("url" + job + "/" + buildno + "/logText/progressiveText?start=" + charNoToStart);
            }
            HttpURLConnection conn = makeRequestToJenkins(url, "GET");
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            progressiveOutput = new StringBuilder();
            while ((outputLine = br.readLine()) != null) {
                progressiveOutput.append("\n");
                progressiveOutput.append(outputLine);
                if (outputLine.contains("Sent message id") && outputLine.contains("to queue")) {
                    LOG.info(outputLine);
                }
            }
            dataKey = headerFields.get("X-More-Data");
            if (!(dataKey != null && dataKey.equals("true"))) {
                conn.disconnect();
                break;
            } else {
                charNoToStart = headerFields.get("X-Text-Size");
            }
            conn.disconnect();
        }
        LOG.info("Console output done.");
    }

    private boolean isBuildSuccessful(JsonObject jsonResponse) {
        LOG.info("Verifying if build is successful");
        String result = jsonResponse.getString("result").trim();
        LOG.info("result : " + result);
        if (result.equals("SUCCESS")) {
            LOG.info("Job: " + job.toUpperCase() + "| env: " + env.toUpperCase() + " | successful run");
            return true;
        } else {
            LOG.info("Job: " + job.toUpperCase() + "| env: " + env.toUpperCase() + " | run FAILED");
            return false;
        }
    }

    private JsonObject getBuildJsonOutput(String buildno, String job) throws IOException {
        URL url;
        if (job.toLowerCase().contains("deploy")) {
            url = new URL("url" + job + "/" + buildno + "/api/json");
        } else {
            url = new URL("url" + job + "/" + buildno + "/api/json");
        }
        LOG.info("JSON Output request --- ");
        HttpURLConnection connection = makeRequestToJenkins(url, "GET");
        JsonReader rdr = Json.createReader(connection.getInputStream());
        JsonObject obj = rdr.readObject();
        connection.disconnect();
        return obj;
    }

    private String getLastBuildNumber(String job) throws IOException {
        URL url;
        if (job.toLowerCase().contains("deploy")) {
            url = new URL("url" + job + "/lastBuild/buildNumber");
        } else {
            url = new URL("url" + job + "/lastBuild/buildNumber");
        }
        LOG.info("Build number request --- ");
        HttpURLConnection connection = makeRequestToJenkins(url, "GET");
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String buildNumber = br.readLine();
        connection.disconnect();
        return buildNumber;
    }

    private HttpURLConnection makeRequestToJenkins(URL url, String requestType) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Authorization", basicAuth);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestMethod(requestType);
        conn.connect();
        responseCode = conn.getResponseCode();
        responseMessage = conn.getResponseMessage();
        headerFields.clear();
        for (int x = 0; x <= conn.getHeaderFields().size(); x++) {
            headerFields.put(conn.getHeaderFieldKey(x), conn.getHeaderField(x));
        }
        headerLocation = conn.getHeaderField("Location");
        return conn;
    }

    public void validateJenkinsConsoleOutput(String [] textToValidate){
        for (String text : textToValidate) {
            Assert.assertTrue(progressiveOutput.toString().toLowerCase().contains(text.toLowerCase()));
        }
    }

    public static void runJobWithName(String jobName, String params) {
        LOG.info("Running job " + jobName + " with parameters " + params);
        switch (jobName) {
            case "Auto-Invest":
                run(environment, JenkinsJobRunner.Job.AUTO_INVEST, params);
                break;
            case "Monthly-Interest-Income":
                break;
            case "interest-arrears-pay":
                run(environment, JenkinsJobRunner.Job.INTEREST_ARREARS_PAY, params);
                break;
            case "redeem-tranches":
                LOG.info("setJobScheduledTime=" + ActiveData.getRecord("jobScheduledTime") + " set jobScheduledTimeDateFormat=" + ActiveData.getRecord("jobScheduledTimeDateFormat"));
                Timer.waitUntil(ActiveData.getRecord("jobScheduledTime"), ActiveData.getRecord("jobScheduledTimeDateFormat"));
                JenkinsJobRunner.run(environment, JenkinsJobRunner.Job.TRANCHES_REDEEM, params);
                break;
            case "Generate-Annual-statements":
                JenkinsJobRunner.run(environment, JenkinsJobRunner.Job.GENERATE_ANNUAL_STATEMENTS, params);
                break;
            default:
                throw new RuntimeException(jobName + " jobName is incorrect, Please enter the correct name");
        }
    }
}
