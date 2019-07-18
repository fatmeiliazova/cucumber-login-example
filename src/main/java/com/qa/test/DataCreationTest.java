package com.qa.test;

import com.qa.test.API.*;
import com.qa.util.DBOperations;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class DataCreationTest {

    private String userID;
    private String customerID;
    private User user;
    private TestContext testContext;

    public DataCreationTest(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("^I have a '(.*)' user with email '(.*)' and tax details$")
    public void createUser(String typeOfUser, String email) {
        if (typeOfUser.equalsIgnoreCase("verifiedAndEligible")) {
            testContext.setEmail(email);
            user = User.builder().email(email).build();
            UserDetails userDetails = UserDetails.builder().email(user.getEmail()).build();
            userID = user.createAVerifiedUser(userDetails);
            customerID = Customer.createIndividualCustomer(userID);
            Investor.createIndividualInvestor(customerID);
            Investor.selectInvestorCategory(Investor.Category.HIGH_NET_WORTH_INVESTOR, customerID, user);
            Investor.passTheQuizOnTheThirdAttempt(customerID, user);
            Customer.submitTaxRegistration(user, customerID);
        }
    }

    @Given("^I create a verified user with email '(.*)'$")
    public void createVerifiedUser(String email) {
        user = User.builder().email(email).build();
        UserDetails userDetails = UserDetails.builder().email(user.getEmail()).build();
        userID = user.createAVerifiedUser(userDetails);
        customerID = Customer.createIndividualCustomer(userID);
        Investor.createIndividualInvestor(customerID);
    }

    @Given("^I create a verified user with email '(.*)' and select an investor category but do not take quiz$")
    public void createVerifiedUserChooseCategory(String email) {
        user = User.builder().email(email).build();
        UserDetails userDetails = UserDetails.builder().email(user.getEmail()).build();
        userID = user.createAVerifiedUser(userDetails);
        customerID = Customer.createIndividualCustomer(userID);
        Investor.createIndividualInvestor(customerID);
        Investor.selectInvestorCategory(Investor.Category.HIGH_NET_WORTH_INVESTOR, customerID, user);
    }

    @Given("^I create a verified user with email '(.*)' and declare myself ineligible$")
    public void createVerifiedUserChooseIneligible(String email) {
        user = User.builder().email(email).build();
        UserDetails userDetails = UserDetails.builder().email(user.getEmail()).build();
        userID = user.createAVerifiedUser(userDetails);
        customerID = Customer.createIndividualCustomer(userID);
        Investor.createIndividualInvestor(customerID);
        Investor.selectInvestorCategory(Investor.Category.NONE_OF_THE_ABOVE, customerID, user);
    }

    @Given("^I create a user with email '(.*)' and admin marks the user as ineligible$")
    public void createUserAdminIneligible(String email) {
        user = User.builder().email(email).build();
        UserDetails userDetails = UserDetails.builder().email(user.getEmail()).build();
        userID = user.createAVerifiedUser(userDetails);
        customerID = Customer.createIndividualCustomer(userID);
        Investor.createIndividualInvestor(customerID);
        Investor.selectInvestorCategory(Investor.Category.HIGH_NET_WORTH_INVESTOR, customerID, user);
        Investor.passTheQuizOnTheThirdAttempt(customerID, user);
        Admin.setInvestorCategoryFromAdmin(Investor.Category.NONE_OF_THE_ABOVE, customerID);
    }

    @Given("I create a user with email '(.*)' with failed kyc verification$")
    public void createAnIneligibleUserWithFailedKyc(String email) {
        user = User.builder().email(email).build();
        UserDetails userDetails = UserDetails.builder().email(user.getEmail()).build();
        userID = user.createAUserThatFailsElectronicVerification(userDetails);
        customerID = Customer.createIndividualCustomer(userID);
        Investor.createIndividualInvestor(customerID);
    }

    @Given("I create an '(.*)' user with email '(.*)' with failed kyc verification$")
    public void createAnEligibleUserWithFailedKyc(String status, String email) {
        user = User.builder().email(email).build();
        UserDetails userDetails = UserDetails.builder().email(user.getEmail()).build();
        userID = user.createAUserThatFailsElectronicVerification(userDetails);
        customerID = Customer.createIndividualCustomer(userID);
        Investor.createIndividualInvestor(customerID);
        if (status.equalsIgnoreCase("eligible")) {
            Investor.selectInvestorCategory(Investor.Category.HIGH_NET_WORTH_INVESTOR, customerID, user);
            Investor.passTheQuizOnTheThirdAttempt(customerID, user);
        }
    }

    @Given("I create an '(.*)' user with email '(.*)' with failed kyc verification and tax details$")
    public void createAnEligibleUserWithFailedKycAndTaxDetails(String status, String email) {
        createAnEligibleUserWithFailedKyc(status, email);
        Customer.submitTaxRegistration(user, customerID);
    }

    @Given("I create a user with email '(.*)' with failed watchlist verification$")
    public void createAnIneligibleUserWithFailedWatchList(String email) {
        user = User.builder().email(email).build();
        UserDetails userDetails = UserDetails.builder().email(user.getEmail()).build();
        userID = user.createAUserThatFailsWatchListVerification(userDetails);
        customerID = Customer.createIndividualCustomer(userID);
        Investor.createIndividualInvestor(customerID);
    }

    @Given("I create an '(.*)' user with email '(.*)' with failed watchlist verification$")
    public void createAnEligibleUserWithFailedWatchList(String status, String email) {
        user = User.builder().email(email).build();
        UserDetails userDetails = UserDetails.builder().email(user.getEmail()).build();
        userID = user.createAUserThatFailsWatchListVerification(userDetails);
        customerID = Customer.createIndividualCustomer(userID);
        Investor.createIndividualInvestor(customerID);
        if (status.equalsIgnoreCase("eligible")) {
            Investor.selectInvestorCategory(Investor.Category.HIGH_NET_WORTH_INVESTOR, customerID, user);
            Investor.passTheQuizOnTheThirdAttempt(customerID, user);
        }
    }

    @Given("^I create a user with email '(.*)' with pending Electronic Verification$")
    public void createAUserWithPendingElectronicVerification(String email) {
        user = User.builder().email(email).build();
        UserDetails userDetails = UserDetails.builder().email(user.getEmail()).build();
        userID = user.createAuserWithPendingElectronicVerification(userDetails);
        customerID = Customer.createIndividualCustomer(userID);
        Investor.createIndividualInvestor(customerID);
    }

    @Given("^I create an '(.*)' user with email '(.*)' with pending Electronic Verification$")
    public void createAUserWithPendingElectronicVerification(String status, String email) {
        user = User.builder().email(email).build();
        UserDetails userDetails = UserDetails.builder().email(user.getEmail()).build();
        userID = user.createAuserWithPendingElectronicVerification(userDetails);
        customerID = Customer.createIndividualCustomer(userID);
        Investor.createIndividualInvestor(customerID);
        if (status.equalsIgnoreCase("eligible")) {
            Investor.selectInvestorCategory(Investor.Category.HIGH_NET_WORTH_INVESTOR, customerID, user);
            Investor.passTheQuizOnTheThirdAttempt(customerID, user);
        }
    }

    @Given("I create a user with email '(.*)' with passed Digital verification")
    public void createAUserWithFailedElectronicAndPassedDigitalVerification(String email) {
        user = User.builder().email(email).build();
        UserDetails userDetails = UserDetails.builder().email(user.getEmail()).build();
        userID = user.createAuserThatFailsElectronicAndPassesDigitalVerification(userDetails);
        customerID = Customer.createIndividualCustomer(userID);
        Investor.createIndividualInvestor(customerID);
    }

    @Given("I create an '(.*)' user with email '(.*)' with passed Digital verification")
    public void createAnEligibleUserWithFailedElectronicAndPassedDigitalVerification(String status, String email) {
        user = User.builder().email(email).build();
        UserDetails userDetails = UserDetails.builder().email(user.getEmail()).build();
        userID = user.createAuserThatFailsElectronicAndPassesDigitalVerification(userDetails);
        customerID = Customer.createIndividualCustomer(userID);
        Investor.createIndividualInvestor(customerID);
        if (status.equalsIgnoreCase("eligible")) {
            Investor.selectInvestorCategory(Investor.Category.HIGH_NET_WORTH_INVESTOR, customerID, user);
            Investor.passTheQuizOnTheThirdAttempt(customerID, user);
        }
    }

    @Given("^I create a user with email '(.*)' with failed electronic verification and failed digital verification$")
    public void createAUserWithFailedElectronicAndDigitalVerification(String email) {
        user = User.builder().email(email).build();
        UserDetails userDetails = UserDetails.builder().email(user.getEmail()).build();
        userID = user.createAuserThatFailsElectronicAndFailsDigitalVerification(userDetails);
        customerID = Customer.createIndividualCustomer(userID);
        Investor.createIndividualInvestor(customerID);
    }

    @Given("^I create an '(.*)' user with email '(.*)' with failed electronic verification and failed digital verification$")
    public void createAnEligibleUserWithFailedElectronicAndDigitalVerification(String status, String email) {
        user = User.builder().email(email).build();
        UserDetails userDetails = UserDetails.builder().email(user.getEmail()).build();
        userID = user.createAuserThatFailsElectronicAndFailsDigitalVerification(userDetails);
        customerID = Customer.createIndividualCustomer(userID);
        Investor.createIndividualInvestor(customerID);
        if (status.equalsIgnoreCase("eligible")) {
            Investor.selectInvestorCategory(Investor.Category.HIGH_NET_WORTH_INVESTOR, customerID, user);
            Investor.passTheQuizOnTheThirdAttempt(customerID, user);
        }
    }

    @Given("^I create a user with email '(.*)' with digital verification in progress")
    public void createAUserWithDigitalVerificationInProgress(String email) {
        user = User.builder().email(email).build();
        UserDetails userDetails = UserDetails.builder().email(user.getEmail()).build();
        userID = user.createAuserWithPendingDigitalVerification(userDetails);
        customerID = Customer.createIndividualCustomer(userID);
        Investor.createIndividualInvestor(customerID);
    }

    @Given("^I create an '(.*)' user with email '(.*)' with digital verification in progress")
    public void createAnEligibleUserWithDigitalVerificationInProgress(String status, String email) {
        user = User.builder().email(email).build();
        UserDetails userDetails = UserDetails.builder().email(user.getEmail()).build();
        userID = user.createAuserWithPendingDigitalVerification(userDetails);
        customerID = Customer.createIndividualCustomer(userID);
        Investor.createIndividualInvestor(customerID);
        if (status.equalsIgnoreCase("eligible")) {
            Investor.selectInvestorCategory(Investor.Category.HIGH_NET_WORTH_INVESTOR, customerID, user);
            Investor.passTheQuizOnTheThirdAttempt(customerID, user);
        }
    }


    private void addAnAttribute(String additionalAttribute) {
        if (additionalAttribute.equalsIgnoreCase("debitCard")) {
            Customer.addDebitCard(userID, customerID);
        } else if (additionalAttribute.equalsIgnoreCase("bankAccount")) {
            Customer.createAVerifiedBankAccount(customerID, user, BankAccount.builder().build());
        } else if (additionalAttribute.equalsIgnoreCase("balance")) {
            addMoneyViaBankAccount("10000.00");
        }
    }

    private void addMoneyViaBankAccount(String amount) {
        BankAccount bankAccount = BankAccount.builder().build();
        testContext.getBankDetails().setAccountOwner(bankAccount.getBankAccountHolder());
        testContext.getBankDetails().setAccountNumber(bankAccount.getBankAccountNumber());
        testContext.getBankDetails().setSortCode(bankAccount.getBankSortCode());
        Customer.createAVerifiedBankAccount(customerID, user, bankAccount);
        BankAccount.transferMoneyFromBankAccountToInvestorAccount(bankAccount.getBankAccountNumber(), bankAccount.getBankSortCode(), amount, customerID, Investor.getInvestorAccountID(customerID));
    }

    @Given("^I have a '(.*)' user with a '(.*)' with email '(.*)' and tax details$")
    public void verifiedAndEligibleUserWithDebitCard(String typeOfUser, String additionalAttribute, String email) {
        createUser(typeOfUser, email);
        addAnAttribute(additionalAttribute);
    }

    @Given("^I have a '(.*)' user '(.*)' with balance '(.*)' and tax details$")
    public void verifiedAndEligibleUserWithBalanceAndTaxDetails(String typeOfUser, String emails, String balance) {
        String[] emailIDs = emails.split(",");
        for (String emailID : emailIDs) {
            testContext.setEmail(emailID);
            createUser(typeOfUser, emailID);
            addMoneyViaBankAccount(balance);
        }
    }

    @Given("^I have a '(.*)' user '(.*)' with some balance$")
    public void verifiedAndEligibleUserWithSomeBalance(String typeOfUser, String email) {
        createUser(typeOfUser, email);
        addMoneyViaBankAccount("1000.00");
    }

    @Then("^I create an Unverfied user with email '(.*)'$")
    public void createUnverifiedUser(String email) {
        user = User.builder().email(email).build();
        UserDetails userDetails = UserDetails.builder().email(user.getEmail()).build();
        userID = user.createUnverifiedUser(userDetails);
        customerID = Customer.createIndividualCustomer(userID);
        Investor.createIndividualInvestor(customerID);
    }

    @Then("I have an Unverified user with email '(.*)'")
    public void createAnUnverifiedUser(String email) {
        user = User.builder().email(email).build();
        UserDetails userDetails = UserDetails.builder().email(user.getEmail()).build();
        userID = user.createUnverifiedUser(userDetails);
    }

    @Then("I create a company customer '(.*)' with email")
    public void createCompanyCustomer(String email) {
        Customer customer = Customer.builder().user_email(email).build();
        customer.createCompanyCustomer();
    }

    @Then("^The user makes an investment of '(.*)' in the loan")
    public void createUserWhoInvestsInLoan(String amount) {
        String loanID = testContext.getLoan().getLoanID();
        String customerID = DBOperations.getInvestorIDFromUserEmail(testContext.getEmail());
        User user = User.builder().email(testContext.getEmail()).build();
        Loan.investInATrancheOfALoan(amount, loanID, customerID, user);
    }

    @Then("^I create an unverified bank account using API")
    public void createUnverifiedBankAccount() {
        BankAccount account = BankAccount.builder().build();
        String customerID = DBOperations.getInvestorIDFromUserEmail(testContext.getEmail());
        Customer.createAnUnverifiedBankAccount(customerID, account);
    }

    @Then("I upload bank statement using API")
    public void uploadingBankStatement() {
        String bankAccountID = DBOperations.getBankAccountIDFromUserEmail(testContext.getEmail());
        String customerID = DBOperations.getInvestorIDFromUserEmail(testContext.getEmail());
        Customer.uploadBankAccountStatement(bankAccountID, customerID, user);
    }

    @Then("^I add a new debit card with card number '(.*)' expiry month as '(.*)' and expiry year as '(.*)'$")
    public void addANewDebitCard(String cardNumber, String expiryMonth, String expiryYear) {
        testContext.getDebitCardDetails()
                .setCardNumber(cardNumber)
                .setExpiryMonth(expiryMonth)
                .setExpiryYear(expiryYear);
        Customer.addDebitCardWithGivenValues(userID, customerID, cardNumber, expiryMonth, expiryYear);
    }

}