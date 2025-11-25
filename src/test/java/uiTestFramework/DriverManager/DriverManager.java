package uiTestFramework.DriverManager;

import org.openqa.selenium.WebDriver;
import uiTestFramework.config.Config;

import java.lang.module.Configuration;
import java.time.Duration;

public class DriverManager {

    /*1. Creates a TreadSafe Web driver instance
    2. Would be singleton i.e. only one instance for each thread
    3. Returns web driver object based on the config.properties
    4. (To do) Config or cross browser testing needs to be done iof required*/


    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private DriverManager(){
        //prevent instantiation
    }

    public static void setDriver(){
        if(driver.get() == null){
            driver.set(DriverFactory.createInstance());
            driver.get().manage().window().maximize();
        }
    }

    public static WebDriver getDriver(){
        return driver.get();
    }

    public static void quitDriver(){
        if(driver.get() != null){ // Change == to !=
            try {
                driver.get().quit();
            } catch (Exception e) {
                System.err.println("Unable to quit driver: " + e.getMessage());
            } finally {
                driver.remove(); // Always clean up the ThreadLocal
            }
        }
    }








}
