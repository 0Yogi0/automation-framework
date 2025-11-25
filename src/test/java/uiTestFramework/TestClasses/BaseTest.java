package uiTestFramework.TestClasses;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.*;
import uiTestFramework.DriverManager.DriverManager;

import uiTestFramework.Utilities.LoggerUtil;
import uiTestFramework.config.Config;
import uiTestFramework.extentReportManagers.ExtentManager;
import uiTestFramework.extentReportManagers.ExtentTestManager;
import uiTestFramework.listeners.TestListener;

import java.lang.reflect.Method;

@Listeners({TestListener.class})
public abstract class BaseTest{

    private static final Logger log = LoggerUtil.getLogger(BaseTest.class);

    protected WebDriver driver;

    @BeforeSuite
    public void setupSuite(ITestContext context) {
        String suiteName = context.getSuite().getName();
        ExtentManager.createInstance(suiteName);   // Initialize report
    }

    @BeforeMethod
    public void setup(Method method) {
        ExtentTestManager.startTest(method.getName());
        DriverManager.setDriver();// Initialize WebDriver
        driver = DriverManager.getDriver();
        log.info("Driver initialized successfully for test: {}", method.getName());
        driver.get(Config.getConfigInstance().getBaseUrl());
    }

    @AfterMethod
    public void tearDown() {
        log.info("Closing WebDriver");
        DriverManager.quitDriver();
        log.info("WebDriver Closed");
        ExtentTestManager.endTest();
    }

    @AfterSuite
    public void flushSuite() {
        ExtentManager.getExtent().flush();
    }

}





