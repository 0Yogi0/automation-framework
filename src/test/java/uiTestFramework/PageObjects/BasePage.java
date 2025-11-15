package uiTestFramework.PageObjects;

import io.reactivex.rxjava3.internal.operators.flowable.FlowableCache;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@SuppressWarnings("LoggingSimilarMessage")
public abstract class BasePage {

    private static final int DEFAULT_TIMEOUT = 20;

    protected Logger log;
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver,Logger logger){
        this.driver = driver;
        this.log = logger;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
    }

    /************* wait action methods *************/

    protected WebElement waitForVisibilityOfElement(By locator,String elementName){
        log.info("Waiting for Visibility of element: {} using by locator",elementName);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForVisibilityOfElement(WebElement element,String elementName){
        log.info("Waiting for Visibility of element: {}",elementName);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected boolean waitForInvisibilityOfElement(By locator,String elementName){
        log.info("Waiting for invisibility of element: {} by locator",elementName);
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    protected boolean waitForInvisibilityOfElement(WebElement element,String elementName){
        log.info("Waiting for invisibility of element: {}",elementName);
        return wait.until(ExpectedConditions.invisibilityOf(element));
    }

    protected WebElement waitForElementToBeClickable(By locator, String elementName){
        log.info("Waiting for element to be clickable: {}", elementName);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected WebElement waitForElementToBeClickable(WebElement element, String elementName){
        log.info("Waiting for element to be clickable: {}", elementName);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /************* Click action methods *************/

    protected void clickOnElement(By locator,String elementName){
        waitForElementToBeClickable(locator,elementName);
        log.info("Clicking on element: {}", elementName);
        driver.findElement(locator).click();
    }

    protected void clickOnElement(WebElement element,String elementName){
        waitForElementToBeClickable(element,elementName);
        log.info("Clicking on element: {}", elementName);
        element.click();
    }

    protected void javascriptClick(WebElement element,String elementName){

        waitForElementToBeClickable(element,elementName);

        log.info("JavaScript Executor clicking on element: {}", elementName);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",element);
        log.info("JavaScript Executor clicked on element: {}",elementName);
    }

    protected void javascriptClick(By locator,String elementName){
        WebElement element = waitForElementToBeClickable(locator,elementName);
        log.info("JavaScript Executor clicking on element: {}", elementName);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",element);
        log.info("JavaScript Executor clicked on element: {}",elementName);
    }

    /************* Get and set values methods *************/

    protected String getText(By locator,String elementName){
        waitForVisibilityOfElement(locator,elementName);
        log.info("Getting text from element: {}",elementName);
        return driver.findElement(locator).getText();
    }

    protected String getText(WebElement element,String elementName){
        waitForVisibilityOfElement(element,elementName);
        log.info("Getting text from element: {}",elementName);
        return element.getText();
    }

    protected void enterText(By locator,String elementName,String textToEnter){
        waitForElementToBeClickable(locator,elementName);
        log.info("Entering text in the element: {}",elementName);
        WebElement element = driver.findElement(locator);

        element.clear();
        element.sendKeys(textToEnter);
    }

    protected void enterText(WebElement element,String elementName,String textToEnter){
        waitForElementToBeClickable(element,elementName);
        log.info("Entering text in the element: {}",elementName);
        element.clear();
        element.sendKeys(textToEnter);
    }

    protected String getAttribute(By locator,String elementName, String attribute){
        waitForVisibilityOfElement(locator,elementName);
        log.info("Getting attribute: {} from element: {}",attribute,elementName);
        return driver.findElement(locator).getAttribute(attribute);
    }

    protected String getAttribute(WebElement element,String elementName, String attribute){
        waitForVisibilityOfElement(element,elementName);
        log.info("Getting attribute: {} from element: {}",attribute,elementName);
        return element.getAttribute(attribute);
    }

    /************* miscellaneous action methods *************/

    protected void scrollIntoView(By locator,String elementName) {
        log.info("Scrolling into view: {}", elementName);
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    protected void scrollIntoView(WebElement element,String elementName) {
        log.info("Scrolling into view: {}", elementName);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }



}
