package uiTestFramework.DriverManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import uiTestFramework.config.BrowserType;
import uiTestFramework.config.Config;

public class DriverFactory {

    public static WebDriver createInstance(){
        BrowserType browser = Config.getConfigInstance().getBrowser();

        switch (browser){

            case CHROME -> {
                ChromeOptions options = new ChromeOptions();
                return new ChromeDriver(options);
            }

            case EDGE -> {
                return new EdgeDriver();
            }

            case SAFARI -> {
                return new SafariDriver();
            }

            case FIREFOX -> {
                return new FirefoxDriver();
            }

            case REMOTE -> throw new UnsupportedOperationException("Remote execution not implemented yet.");

            default -> throw new IllegalArgumentException("Err: Invalid browser "+browser);
        }
    }

}
