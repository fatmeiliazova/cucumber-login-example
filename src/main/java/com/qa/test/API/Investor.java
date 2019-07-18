package com.qa.test.API;

import com.google.gson.Gson;
import com.qa.test.InvestorPlatformUI;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class Investor {
    private static Logger LOG = LoggerFactory.getLogger(Investor.class);

    public enum Category {
        HIGH_NET_WORTH_INVESTOR("high_net_worth_investor"),
        SOPHISTICATED_INVESTOR("sophisticated_investor"),
        INVESTMENT_PROFESSIONAL_COMPANY("investment_professional"),
        PROFESSIONAL_INVESTOR("professional_investor"),
        NONE_OF_THE_ABOVE("ineligible"),
        SOPHISTICATED_COMPANY_INVESTOR("professional_client");
        private final String url;

        Category(String url) {
            this.url = url;
        }

        public String getCategory() {
            return url;
        }
    }

    public static void selectInvestorCategory(Category investorCategory, String customerID, User user) {
        RequestSpecification request = Request.builder().build()
                .header("Authorization", "Bearer {" + Login.getJwtTokenForAUser(user) + "}");
        JSONObject requestBody = new JSONObject();
        requestBody.put("investor_category", investorCategory.getCategory());
        request.body(requestBody.toJSONString());
        ValidatableResponse response = request.put("/investors/" + customerID + "/investor-category").then();
        response.assertThat().statusCode(200);
    }

    private static void answerTheQuiz(String customerID, String quiz, User user) {
        RequestSpecification request = Request.builder().build()
                .header("Authorization", "Bearer {" + Login.getJwtTokenForAUser(user) + "}");
        request.body(quiz);
        ValidatableResponse response = request.post("/investors/" + customerID + "/quiz-certification").then();
        response.assertThat().statusCode(201);

    }

    private static Response getInvestorWithID(String customerID) {
        RequestSpecification request = Request.builder().build()
                .header("X-AUTH-TOKEN", InvestorPlatformUI.testParams.getServiceWorker());
        ValidatableResponse response = request.get("/investors/" + customerID).then();
        response.assertThat().statusCode(200);
        return response.extract().response();
    }

    public static String getInvestorAccountID(String customerID) {
        Response investor = getInvestorWithID(customerID);
        return investor.getBody().path("data.accounts[0].id");
    }

    public static void updateAutoWithdraw(String bankAccountID, String customerID) {
        String investorAccountID = getInvestorAccountID(customerID);
        RequestSpecification request = Request.builder().build()
                .header("X-AUTH-TOKEN", InvestorPlatformUI.testParams.getServiceWorker());
        JSONObject requestBody = new JSONObject();
        requestBody.put("bank_account_id", bankAccountID);
        requestBody.put("enabled", true);
        request.body(requestBody.toJSONString());
        ValidatableResponse response = request.put("/investors/" + customerID + "/accounts/" + investorAccountID + "/auto-withdraw").then();
        response.assertThat().statusCode(200);
    }

    public static void createIndividualInvestor(String customerID) {
        RequestSpecification request = Request.builder().build()
                .header("X-AUTH-TOKEN", InvestorPlatformUI.testParams.getServiceWorker());
        ValidatableResponse response = request.post("/investors/connect-customer/" + customerID).then();
        response.assertThat().statusCode(201);
    }

    public static void passTheQuizOnTheFirstAttempt(String customerID, User user) {
        answerTheQuiz(customerID, Quiz.getAnswersToQuizToPassItOnFirstAttempt(), user);
    }

    public static void passTheQuizOnTheSecondAttempt(String customerID, User user) {
        answerTheQuiz(customerID, Quiz.getAnswersToQuizToFailItOnFirstAttempt(), user);
        answerTheQuiz(customerID, Quiz.getAnswersToQuizToPassItOnSecondAttempt(), user);
    }

    public static void passTheQuizOnTheThirdAttempt(String customerID, User user) {
        answerTheQuiz(customerID, Quiz.getAnswersToQuizToFailItOnFirstAttempt(), user);
        answerTheQuiz(customerID, Quiz.getAnswersToQuizToFailItOnSecondAttempt(), user);
        answerTheQuiz(customerID, Quiz.getAnswersToQuizToPassItOnThirdAttempt(), user);
    }

    public static void failTheQuiz(String customerID, User user) {
        answerTheQuiz(customerID, Quiz.getAnswersToQuizToFailItOnFirstAttempt(), user);
        answerTheQuiz(customerID, Quiz.getAnswersToQuizToFailItOnSecondAttempt(), user);
        answerTheQuiz(customerID, Quiz.getAnswersToQuizToFailItOnThirdAttempt(), user);
    }


    public String toJsonString() {
        return GSON.createJSONString(this);
    }
}
