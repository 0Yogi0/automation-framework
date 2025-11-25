package uiTestFramework.PageObjects;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import uiTestFramework.Utilities.LoggerUtil;

public class HomePage extends BasePageV2{


    public HomePage(WebDriver driver) {
        super(driver, LoggerUtil.getLogger(HomePage.class));
        PageFactory.initElements(driver,this);
        log.info("HomePage initialized");
    }
}
