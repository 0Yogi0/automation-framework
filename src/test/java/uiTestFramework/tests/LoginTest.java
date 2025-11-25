package uiTestFramework.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import uiTestFramework.DriverManager.DriverManager;
import uiTestFramework.PageObjects.OrangeHrmLoginPage;
import uiTestFramework.TestClasses.BaseTest;

public class LoginTest extends BaseTest {
    OrangeHrmLoginPage ormLoginPage;
    @BeforeMethod
    void pageSetup(){
       ormLoginPage = new OrangeHrmLoginPage(DriverManager.getDriver());
    }

    @Test
    void TC_01LoginWithValidCredentials() throws InterruptedException {

        ormLoginPage.enterUserName("admin");
        ormLoginPage.enterPassword("admin123");
        ormLoginPage.clickOnLoginBtn();

    }

}
