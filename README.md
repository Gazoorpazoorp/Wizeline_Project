# Wizeline_Project
Wizeline Project for the Automation Bootcamp application.

Introduction.

The present work is a simple test suite to run the scenarios provided in the Wizeline QA Automation Bootcamp document. It is integrated with a Reporting tool 
for a better visualization of the test results and a way to execute the test cases all together in a test suite. It also provides the option to use multiple browsers (Chrome and Firefox). The project was developed using Linux.

Instructions to run the test scripts.

  Test execution.
  
To execute the scrips, please go to package swagLabs. All 7 classes contained in the package represent the test cases.

  Browser switch.
  
In order to run the scripts in a different browser, please check comments after ChromeDriver is declared. Default is Chrome, but the scripts contain the lines to switch
to Firefox by uncommenting the FirefoxDriver lines.

  Reporting tool.

ExtentReports_swagLabs.html file is located under test-output folder and can be opened after each test execution to launch the embedded ExtentReports and check the
test results.

  Run as a TestSuite

To execute a Test Suite containing all the test cases, please use the Suite.xml file located under test-output folder. Right-click the Suite.xml file and select 
Run As > Run Configurations... A pop-up windows will be prompted. Please double-click SuiteAllTestCases under Launch Group section from left panel. This will start the execution to run in parallel.
An html specific for each test case will be added (or updated if ran before) to the test-output folder.

  Technical details.

The required dependencies that need to be added to the POM are: 
org.seleniumhq.selenium & org.testng, 
org.apache.poi for the test data provider (.xlsx)
& com.aventstack for the ExtentReport as reporting tool


@Pedro_Zarur
