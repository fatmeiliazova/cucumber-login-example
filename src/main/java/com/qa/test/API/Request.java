package com.qa.test.API;

import com.qa.test.InvestorPlatformUI;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.LogConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;
import static io.restassured.config.HeaderConfig.headerConfig;

public class Request{

    private static Logger log = LoggerFactory.getLogger(Request.class);

    public static RequestSpecificationBuilder builder() {
        return new RequestSpecificationBuilder();
    }

    public static class RequestSpecificationBuilder {

        private String baseURI = InvestorPlatformUI.testParams.getApiUrl();
        private String accept = "application/json";
        private ContentType contentType = ContentType.JSON;
        private Boolean logOnlyOnError = true;

        RequestSpecification build() {
            RestAssured.baseURI = baseURI;
            RequestSpecification request = given();
            request.config(RestAssured
                    .config()
                    .httpClient(HttpClientConfig
                    .httpClientConfig()
                            .setParam("http.connection.timeout",Integer.parseInt(InvestorPlatformUI.testParams.getAPIRequestTimeout()))
                            .setParam("http.socket.timeout",Integer.parseInt(InvestorPlatformUI.testParams.getAPIRequestTimeout()))
                            .setParam("http.connection-manager.timeout",Integer.parseInt(InvestorPlatformUI.testParams.getAPIRequestTimeout())))
                    .headerConfig(headerConfig().overwriteHeadersWithName("Authorization", "Content-Type")))
                    .config(RestAssured.config().logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails()));
            if (InvestorPlatformUI.testParams.getDebugValue().equals("1") || !logOnlyOnError) {
                request.log().uri().log().body().log().method().log().headers();
            }
            request.accept(accept);
            request.contentType(contentType);
            return request;
        }
    }

}
