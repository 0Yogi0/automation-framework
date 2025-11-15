package uiTestFramework.extentReportManagers;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class ExtentTestManager {

    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static ExtentTest getTest() {
        return test.get();
    }

    public static void startTest(String testName) {
        ExtentReports extent = ExtentManager.getExtent();
        ExtentTest extentTest = extent.createTest(testName);
        test.set(extentTest);
    }

    public static void endTest() {
        test.remove();
    }


}
