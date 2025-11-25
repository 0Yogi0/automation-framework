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
        Config config = Config.getConfigInstance();
        BrowserType browser = config.getBrowser();

        switch (browser){

            case CHROME -> {

                ChromeOptions options = getChromeOptions(config);

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

    private static ChromeOptions getChromeOptions(Config config) {
        ChromeOptions options = new ChromeOptions();
        //check for headless mode
        if (config.isHeadless()) {
            // Use 'new' for modern Chrome headless mode
            options.addArguments("--headless=new");
        }

        // Add other useful options (optional but recommended)
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-gpu"); // Sometimes needed for Linux/CI runs
        return options;
    }

}
