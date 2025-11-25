package uiTestFramework.TestClasses;


import org.apache.logging.log4j.Logger;
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

    @BeforeSuite
    public void setupSuite() {
        String suiteName = "automation framework";//context.getSuite().getName();
        ExtentManager.createInstance(suiteName);// Initialize report
        System.out.println("in before suite baseTest");
    }

    @BeforeMethod
    public void setup(Method method) {

        DriverManager.setDriver();
        // Log specifically using the thread-safe driver
        LoggerUtil.getLogger(BaseTest.class).info("Driver started for: " + method.getName());

        // Use the getter directly
        DriverManager.getDriver().get(Config.getConfigInstance().getBaseUrl());
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





