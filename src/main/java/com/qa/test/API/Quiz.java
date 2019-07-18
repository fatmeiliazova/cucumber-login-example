package com.qa.test.API;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Quiz {
    private static Logger LOG = LoggerFactory.getLogger(Quiz.class);

    public static String getAnswersToQuizToPassItOnFirstAttempt() {


        return "{\n" +
                "\t\"answers\": {\n" +
                "\t\t\"0\": {\n" +
                "\t\t\t\"answer\": \"Investing your money across a range of diversified investments.\",\n" +
                "\t\t\t\"slug\": \"question_slug_1\"\n" +
                "\t\t},\n" +
                "\t\t\"1\": {\n" +
                "\t\t\t\"answer\": \"Once the loan has been repaid or recovered in full, regardless of the term of the loan (including any extension period).\",\n" +
                "\t\t\t\"slug\": \"question_slug_2\"\n" +
                "\t\t},\n" +
                "\t\t\"2\": {\n" +
                "\t\t\t\"answer\": \"The value of any underlying property may go up or down, regardless of the independent valuation of the property and I might lose all or part of my investment.\",\n" +
                "\t\t\t\"slug\": \"question_slug_3\"\n" +
                "\t\t},\n" +
                "\t\t\"3\": {\n" +
                "\t\t\t\"answer\": \"Means I am not guaranteed to get my money back, because the borrower may still default and the value of the property could fall.\",\n" +
                "\t\t\t\"slug\": \"question_slug_4\"\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";
    }

    public static String getAnswersToQuizToPassItOnSecondAttempt() {
        return "{\n" +
                "\t\"answers\": {\n" +
                "\t\t\"0\": {\n" +
                "\t\t\t\"answer\": \"I will not be able to exit my investment and I will need to wait until the loan is repaid by the borrower.\",\n" +
                "\t\t\t\"slug\": \"question_slug_1\"\n" +
                "\t\t},\n" +
                "\t\t\"1\": {\n" +
                "\t\t\t\"answer\": \"Will be repaid early if the borrower repays the loan prior to its original expiry date.\",\n" +
                "\t\t\t\"slug\": \"question_slug_2\"\n" +
                "\t\t},\n" +
                "\t\t\"2\": {\n" +
                "\t\t\t\"answer\": \"In an individual property loan, via an unregulated alternative investment fund.\",\n" +
                "\t\t\t\"slug\": \"question_slug_3\"\n" +
                "\t\t},\n" +
                "\t\t\"3\": {\n" +
                "\t\t\t\"answer\": \"The FSCS will cover 100% of my claim up to £50,000, should LendInvest become insolvent and unable to return money I'm owed.\",\n" +
                "\t\t\t\"slug\": \"question_slug_4\"\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";


    }

    public static String getAnswersToQuizToFailItOnFirstAttempt() {
        return "{\n" +
                "\t\"answers\": {\n" +
                "\t\t\"0\": {\n" +
                "\t\t\t\"answer\": \"Invest all of your money into one single investment.\",\n" +
                "\t\t\t\"slug\": \"question_slug_1\"\n" +
                "\t\t},\n" +
                "\t\t\"1\": {\n" +
                "\t\t\t\"answer\": \"Once the loan has been repaid or recovered in full, regardless of the term of the loan (including any extension period).\",\n" +
                "\t\t\t\"slug\": \"question_slug_2\"\n" +
                "\t\t},\n" +
                "\t\t\"2\": {\n" +
                "\t\t\t\"answer\": \"The value of any underlying property may go up or down, regardless of the independent valuation of the property and I might lose all or part of my investment.\",\n" +
                "\t\t\t\"slug\": \"question_slug_3\"\n" +
                "\t\t},\n" +
                "\t\t\"3\": {\n" +
                "\t\t\t\"answer\": \"Means I am not guaranteed to get my money back, because the borrower may still default and the value of the property could fall.\",\n" +
                "\t\t\t\"slug\": \"question_slug_4\"\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";
    }

    public static String getAnswersToQuizToFailItOnSecondAttempt() {
        return "{\n" +
                "\t\"answers\": {\n" +
                "\t\t\"0\": {\n" +
                "\t\t\t\"answer\": \"I can advertise my investment for sale on lendinvest.com.\",\n" +
                "\t\t\t\"slug\": \"question_slug_1\"\n" +
                "\t\t},\n" +
                "\t\t\"1\": {\n" +
                "\t\t\t\"answer\": \"Will be repaid early if the borrower repays the loan prior to its original expiry date.\",\n" +
                "\t\t\t\"slug\": \"question_slug_2\"\n" +
                "\t\t},\n" +
                "\t\t\"2\": {\n" +
                "\t\t\t\"answer\": \"In an individual property loan, via an unregulated alternative investment fund.\",\n" +
                "\t\t\t\"slug\": \"question_slug_3\"\n" +
                "\t\t},\n" +
                "\t\t\"3\": {\n" +
                "\t\t\t\"answer\": \"The FSCS will cover losses due to poor performance of the loans I have invested in.\",\n" +
                "\t\t\t\"slug\": \"question_slug_4\"\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";


    }

    public static String getAnswersToQuizToPassItOnThirdAttempt() {
        return "{\n" +
                "\t\"answers\": {\n" +
                "\t\t\"0\": {\n" +
                "\t\t\t\"answer\": \"The past performance is not necessarily an accurate guide to future performance and not necessarily a reliable indicator of future results.\",\n" +
                "\t\t\t\"slug\": \"question_slug_1\"\n" +
                "\t\t},\n" +
                "\t\t\"1\": {\n" +
                "\t\t\t\"answer\": \"My investment will be locked in until the loan is repaid by the borrower.\",\n" +
                "\t\t\t\"slug\": \"question_slug_2\"\n" +
                "\t\t},\n" +
                "\t\t\"2\": {\n" +
                "\t\t\t\"answer\": \"I risk losing money I have invested in any LendInvest loan or fund (even though a back-up servicer will take over responsibility for servicing the loan).\",\n" +
                "\t\t\t\"slug\": \"question_slug_3\"\n" +
                "\t\t},\n" +
                "\t\t\"3\": {\n" +
                "\t\t\t\"answer\": \"Is paid at the rate stated for each loan that I invest in.\",\n" +
                "\t\t\t\"slug\": \"question_slug_4\"\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";
    }

    public static String getAnswersToQuizToFailItOnThirdAttempt() {
        return "{\n" +
                "\t\"answers\": {\n" +
                "\t\t\"0\": {\n" +
                "\t\t\t\"answer\": \"The past performance information is a reliable indicator of the future results.\",\n" +
                "\t\t\t\"slug\": \"question_slug_1\"\n" +
                "\t\t},\n" +
                "\t\t\"1\": {\n" +
                "\t\t\t\"answer\": \"My investment will be locked in until the loan is repaid by the borrower.\",\n" +
                "\t\t\t\"slug\": \"question_slug_2\"\n" +
                "\t\t},\n" +
                "\t\t\"2\": {\n" +
                "\t\t\t\"answer\": \"I risk losing money I have invested in any LendInvest loan or fund (even though a back-up servicer will take over responsibility for servicing the loan).\",\n" +
                "\t\t\t\"slug\": \"question_slug_3\"\n" +
                "\t\t},\n" +
                "\t\t\"3\": {\n" +
                "\t\t\t\"answer\": \"Is paid at the rate stated for each loan that I invest in.\",\n" +
                "\t\t\t\"slug\": \"question_slug_4\"\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";
    }


}
