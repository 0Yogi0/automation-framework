package uiTestFramework.Utilities;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import uiTestFramework.DriverManager.DriverManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class ScreenshotUtil {

    private ScreenshotUtil(){};//Prevent object creation

    private static final String SCREENSHOT_FOLDER = "screenshots";

    public static ScreenshotData takeScreenShot(String testName){
        WebDriver driver = DriverManager.getDriver();

        if (driver == null) {
            System.err.println("Driver is null. Cannot take screenshot.");
            return new ScreenshotData(null, null); // Return empty or handle gracefully
        }


        File screenshotFolder = new File(SCREENSHOT_FOLDER);

        //create a ss folder if it is missing
        if(!screenshotFolder.exists()){
            screenshotFolder.mkdirs();
        }


        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());

        String screenshotPath = SCREENSHOT_FOLDER + "/" + testName + "_" + timestamp + ".png";

        File scrFile = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.FILE);

        File destFile = new File(screenshotPath);

        try {
            Files.copy(scrFile.toPath(),destFile.toPath());
        } catch (IOException e) {
            System.err.println("Err: Could not save screenshot for test - "+testName);
            System.err.println("Err: "+e.getMessage());
        }

        String base64 = "";
        try {
            base64 = Base64.getEncoder().encodeToString(Files.readAllBytes(destFile.toPath()));
        } catch (IOException e) {
            System.err.println("Err: Could not read screenshot file as base64 encoding for test - "+testName);
            System.err.println("Err: "+e.getMessage());
        }
        return new ScreenshotData(screenshotPath,base64);

    }

    public static class ScreenshotData{

        private final String path;

        private final String base64;

        public ScreenshotData(String screenshotPath,String base64){
            this.path = screenshotPath;
            this.base64 = base64;
        }

        public String getPath() {
            return path;
        }

        public String getBase64() {
            return base64;
        }
    }

}
