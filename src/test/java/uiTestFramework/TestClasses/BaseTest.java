package uiTestFramework.TestClasses;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import uiTestFramework.DriverManager.DriverManager;
import uiTestFramework.config.Config;
import uiTestFramework.extentReportManagers.ExtentManager;
import uiTestFramework.extentReportManagers.ExtentTestManager;

import java.lang.reflect.Method;

public class BaseTest {


    protected WebDriver driver;
    protected Logger log;

    @BeforeSuite
    public void setupSuite(ITestContext context) {
        String suiteName = context.getSuite().getName();
        ExtentManager.createInstance(suiteName);   // Initialize report
    }

    @BeforeMethod
    public void setup(Method method) {
        log = LogManager.getLogger(method.getDeclaringClass());

        ExtentTestManager.startTest(method.getName());

        driver = DriverManager.getDriver();   // Initialize WebDriver
        log.info("Driver initialized for test: " + method.getName());
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
        ExtentTestManager.endTest();
    }

    @AfterSuite
    public void flushSuite() {
        ExtentManager.getExtent().flush();
    }

}




