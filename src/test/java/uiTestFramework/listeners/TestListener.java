package uiTestFramework.listeners;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import uiTestFramework.Utilities.ScreenshotUtil;

import uiTestFramework.extentReportManagers.ExtentTestManager;

public class TestListener implements ITestListener{

    @Override
    public void onStart(ITestContext context) {
        System.out.println("Test Execution started for suite : " + context.getName());
    }


    @Override
    public void onTestStart(ITestResult result) {
        ExtentTestManager.startTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTestManager.getTest().log(Status.PASS,"✔ Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        String testName = result.getMethod().getMethodName();

        ScreenshotUtil.ScreenshotData screenshotData = ScreenshotUtil.takeScreenShot(testName);

        ExtentTestManager.getTest().log(Status.FAIL,"❌ Test Failed: "+ result.getThrowable());

        if (screenshotData.getBase64() != null && !screenshotData.getBase64().isEmpty()){
            ExtentTestManager.getTest().fail("Err: Test Failed at - ",MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotData.getBase64()).build());
        }


    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getMethod().getMethodName();

        ScreenshotUtil.ScreenshotData screenshotData = ScreenshotUtil.takeScreenShot(testName);

        ExtentTestManager.getTest().log(Status.SKIP,"⚠ Test Skipped: " + result.getThrowable());
        ExtentTestManager.getTest().skip("Warn: Test Skipped at - ",MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotData.getBase64()).build());
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Test Execution ended for suite : " + context.getName());
    }
}
