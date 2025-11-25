package uiTestFramework.PageObjects;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import uiTestFramework.Utilities.LoggerUtil;

public class OrangeHrmLoginPage extends BasePageV2{
    public OrangeHrmLoginPage(WebDriver driver){
        super(driver, LoggerUtil.getLogger(OrangeHrmLoginPage.class));
    }


}
