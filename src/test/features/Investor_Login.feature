@full-regression
@investor-regression
@login
Feature: As a Investor User navigate to LendInvest service platform and login If credentials are not provided in full or incorrectly, expected error messages should appear

  @valid-login
  Scenario: Log-01 Login with existing user credentials
    Given I have a 'verifiedAndEligible' user with email 'validlogin01@lendinvest.com' and tax details
    Then I login as user 'validlogin01@lendinvest.com' then verify text 'Investment account'

  @invalid-login
  Scenario: Log-02 Login with invalid user credentials
    Given I login as user 'invalid01@lendinvest.com' then verify text 'Your email or password was incorrect.'

  @send-reset-password-email
  Scenario: Send reset password email
    Given I create a verified user with email 'resetPassword01@automation.com'
    And I am on the platform
    And I am on the Login page
    Then I click on the reset password link
    Then I am on the 'ResetPassword' page
    Then Verify 'Reset your password' text
    And I enter the email address as 'resetPassword01@automation.com'
    Then I click on the continue button
    Then Verify 'If there is an account associated with resetPassword01@automation.com you will receive an email with a link to reset your password.' text

  @reset-password-incorrect-email
  Scenario: Send reset password email with incorrect email
    Given I am on the platform
    And I am on the Login page
    Then I click on the reset password link
    Then I am on the 'ResetPassword' page
    Then Verify 'Reset your password' text
    And I enter the email address as 'incorrect@lendinvest.com'
    Then I click on the continue button
    Then Verify 'If there is an account associated with incorrect@lendinvest.com you will receive an email with a link to reset your password.' error message

  @reset-password-invalid-email
  Scenario: Send reset password email with invalid email
    Given I am on the platform
    And I am on the Login page
    Then I click on the reset password link
    Then I am on the 'ResetPassword' page
    Then Verify 'Reset your password' text
    And I enter the email address as 'invalid.com'
    Then I click on the continue button
    Then Verify '"invalid.com" is not a valid email.' error message