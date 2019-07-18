package com.qa.test.API;

import com.qa.test.InvestorPlatformUI;
import com.qa.util.Decode;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class User {

    private static final String DEFAULT_PASSWORD = Decode.decodeText(InvestorPlatformUI.testParams.getUserPassword());
    private static Logger LOG = LoggerFactory.getLogger(User.class);

    private String email;
    private String password;


    public String getEmail() {
        return this.email;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }


    public static class UserBuilder {

        private String email = null;
        private String password = DEFAULT_PASSWORD;

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public User build() {
            User user = new User();
            user.email = this.email;
            user.password = this.password;
            if (user.email == null) {
                throw new RuntimeException(" User email cannot be null");
            }
            return user;
        }
    }

    public String createAVerifiedUser(UserDetails userdetails) {
        String userID = createUser(this);
        updateUserDetails(userdetails, userID);
        stepOneVerification(userID, this);
        customerUserVerification(userID);
        return userID;
    }

    public String createUnverifiedUser(UserDetails userdetails){
        String userID = createUser(this);
        updateUserDetails(userdetails, userID);
        return userID;
    }

    public String createAUserThatFailsElectronicVerification(UserDetails userdetails) {
        userdetails.last_name = "ravelo";
        return createAVerifiedUser(userdetails);
    }

    public String createAUserThatFailsWatchListVerification(UserDetails userdetails) {
        userdetails.last_name = "Escobar";
        return createAVerifiedUser(userdetails);
    }

    public String createAuserThatFailsElectronicAndPassesDigitalVerification(UserDetails userdetails) {
        String userID = createAUserThatFailsElectronicVerification(userdetails);
        passOnfidoDigitalCheck(this, userID);
        return userID;
    }

    public String createAuserThatFailsElectronicAndFailsDigitalVerification(UserDetails userdetails) {
        userdetails.last_name = "allfail";
        String userID = createAVerifiedUser(userdetails);
        passOnfidoDigitalCheck(this, userID);
        return userID;
    }

    public String createAuserWithPendingElectronicVerification(UserDetails userdetails) {
        userdetails.last_name = "kycinprogress";
        return createAVerifiedUser(userdetails);
    }

    public String createAuserWithPendingDigitalVerification(UserDetails userdetails) {
        userdetails.last_name = "digitalinprogress";
        String userID = createAVerifiedUser(userdetails);
        passOnfidoDigitalCheck(this, userID);
        return userID;
    }


    private static String createUser(User user) {
        RequestSpecification request = Request.builder().build();
        request.header("X-AUTH-TOKEN", InvestorPlatformUI.testParams.getServiceWorker());
        request.body(user.toJsonString());
        ValidatableResponse response = request.post("/users").then();
        response.assertThat().statusCode(201);
        return response.extract().body().path("data.id");
    }

    private static void updateUserDetails(UserDetails userDetails, String userID) {
        RequestSpecification request = Request.builder().build();
        request.header("X-AUTH-TOKEN", InvestorPlatformUI.testParams.getServiceWorker());
        request.body(userDetails.toJsonString());
        ValidatableResponse response = request.put("/users/" + userID).then();
        response.assertThat().statusCode(200);
    }

    private static void stepOneVerification(String userID, User user) {
        RequestSpecification request = Request.builder().build();
        request.header("Authorization", "Bearer {" + Login.getJwtTokenForAUser(user) + "}");
        ValidatableResponse response = request.post("/users/" + userID + "/verification-step-one").then();
        response.assertThat().statusCode(201);
    }

    private static void customerUserVerification(String userID) {
        LOG.info("Waiting for 2 seconds for an internal API call to finish");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        RequestSpecification request = Request.builder().build();
        request.header("X-AUTH-TOKEN", InvestorPlatformUI.testParams.getServiceWorker());
        ValidatableResponse response = request.post("/customers/users/" + userID).then();
        response.assertThat().statusCode(201);
    }

    private static void passOnfidoDigitalCheck(User user, String userID) {
        RequestSpecification request = Request.builder().build();
        request.header("Authorization", "Bearer {" + Login.getJwtTokenForAUser(user) + "}");
        ValidatableResponse response = request.post("/users/" + userID + "/onfido-digital-check").then();
        response.assertThat().statusCode(204);
    }

    private static Map<String, Object> getUser(String userID) {
        RequestSpecification request = Request.builder().build();
        request.header("X-AUTH-TOKEN", InvestorPlatformUI.testParams.getServiceWorker());
        ValidatableResponse response = request.get("/users/" + userID).then();
        response.assertThat().statusCode(200);
        JsonPath jsonPath = response.extract().body().jsonPath();
        return jsonPath.getMap("data");
    }

    public String toJsonString() {
        return GSON.createJSONString(this);
    }

}
