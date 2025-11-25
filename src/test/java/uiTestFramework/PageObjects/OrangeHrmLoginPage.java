package uiTestFramework.PageObjects;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import uiTestFramework.Utilities.LoggerUtil;

public class OrangeHrmLoginPage extends BasePageV2{

    public OrangeHrmLoginPage(WebDriver driver){
        super(driver, LoggerUtil.getLogger(OrangeHrmLoginPage.class));
        PageFactory.initElements(driver,this);
        log.info("Login page initialized");
    }

    @FindBy(xpath = "//div[@class='orangehrm-login-branding']")
    private WebElement loginPageLogo;

    @FindBy(xpath = "//input[@placeholder='Username']")
    private WebElement usernameInp;

    @FindBy(xpath = "//input[@placeholder='Password']")
    private WebElement passwordInp;

    @FindBy(xpath = "//button[normalize-space()='Login']")
    private WebElement loginBtn;

    public void enterUserName(String username){
        enterText(usernameInp,"Username input box",username);
    }
    public void enterPassword(String password){
        enterText(passwordInp,"Password input box",password);
    }

    public void clickOnLoginBtn(){
        clickOnElement(loginBtn,"Login Button");
    }


}
