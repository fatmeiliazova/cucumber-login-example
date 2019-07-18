# Web & API Automation Project - Investor Platform
*** Features of the Framework: ***
-----
-  Use of `Selenium Webdriver` for Web UI Automation
-  Use of `REST-assured` for API Automation
-  Use of `Page Object Model` as Design pattern
-  Use of `Cucumber` as BDD Framework
-  Pretty Reports using `Maven-Cucumber-Reporting`
-  Embedded screenshots for verification of failed tests
-  Logging to capture the test execution workflow
-  Configurable parameters i.e. ability to choose different browsers and test environments
-  Automatic Selenium WebDriver binaries management using `WebDriverManager`

*** How to Run ***
-----
* Using IDE like Intellij: 
  * Import the project in IDE
  * Right click `pom.xml` and click `Run As` , Maven >> `Generate sources`. After dependencies are downloaded.
  * Right click again, `pom.xml` and `Run As`, Maven >> `Test`. If it asks to specify the goal, choose as 'verify'

* Another option is to go to the root of the folder and run maven from command line using: `mvn clean compile` and then followed by 
    - `mvn -Dtest=TestRunner -Drun.at=local -Dbrowser=Chrome-Headless -Ddeploy=false -DresetDB=true -Denv=qa1 "-Dcucumber.options=--tags @login" verify`
    - `mvn -Dtest=TestRunner verify`

*** Report to check ***
-----
- This framework generates multiple reports, however the best report to check is: *Cucumber HTML Report*.
- It gives details like: Passed, Failed, Skipped, Pending, Total etc. along with graphs.
- It is available in: `target/cucumber-reports/advanced-reports/cucumber-html-reports` directory and the file to check is: `overview-features.html`


*** Technical Know Hows ***
-----
Framework contains packages:
* Under src/main/java
    * `com.qa.drivers`: package contains `DriverFactory` class which manages the driver initialization for chosen web browser on local or remote server [SauceLabs].
    * `com.qa.objects`: package contains page object model classes, which has the getter-setter methods and page web elements.
    * `com.qa.test`: package contains the step definition classes for both Web and API test feature files.
    * `com.qa.util`: package contains project specific utility classes needed for configuration, DB actions, deploy Jenkins jobs etc.

* Under src/test/java
    * `com.qa.test`: package contains the `TestRunner` class which glues features & steps together and generates `cucumber.json` which is consumed to produce HTML test execution reports
    * `resources/features`: this directory contains the feature files needed for Cucumber in BDD `Given-When-Then` format.
    * `resources/default.conf` to have general parameters like Browsers, URLs, Users etc.
    * `resources/logback-test.xml` to generate logs
    
* Other files
    * `pom.xml`: for Maven dependencies and plugins configuration
    * `testng.xml` : to execute the Cucumber Test runner class
    * `ci/Jenkinsfile.gdsl` : Jenkins pipeline script to run the test suite on Jenkins server inside Docker
    * `ci/saucelabs/Jenkinsfile` : Jenkins pipeline script to run the test suite on Jenkins server with Remote Driver in SauceLabs Cloud
    * `Dockerfile` : Dockerfile to create the docker image of test suite
    * `commands.txt` : file with sample commands to run
-----