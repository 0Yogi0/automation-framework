package uiTestFramework.PageObjects;

import io.reactivex.rxjava3.internal.operators.flowable.FlowableCache;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import uiTestFramework.Utilities.FrameworkException;
import java.time.Duration;

@SuppressWarnings("LoggingSimilarMessage")
public abstract class BasePage {

    private static final int DEFAULT_TIMEOUT = 12;

    protected Logger log;
    protected WebDriver driver;
    protected WebDriverWait wait;



    public BasePage(WebDriver driver,Logger logger){
        this.driver = driver;
        this.log = logger;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
    }

    /*log.error("Element '{}' not found using locator: {}",elementName,locator);
    throw new FrameworkException("Element "+elementName+" not found.",e);*/




    /************* wait action methods *************/

    protected WebElement waitForVisibilityOfElement(By locator,String elementName){
        try {
            log.info("Waiting for Visibility of element: {} using by locator",elementName);
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (StaleElementReferenceException e) {
            log.error("Element '{}' went stale cannot check for visibility by locator {}",elementName,locator);
            throw new FrameworkException("Element "+elementName+" went stale",e);
        }catch (TimeoutException e){
            log.error("⏳ Timeout: '{}' element remains invisible, using locator {}",elementName,locator);
            throw new FrameworkException("Timeout waiting for visibility of: "+elementName,e);
        }catch (NoSuchElementException e){
            log.error("No such element: {} found with locator {}",elementName,locator,e);
            throw new FrameworkException("No such element: "+elementName+" found",e);
        }catch (Exception e) {
            log.error("⚠ Unexpected error during wait for visibility of '{}'",elementName, e);
            throw new FrameworkException("Unexpected wait error for visibility of: " + elementName, e);
        }
    }

    protected WebElement waitForVisibilityOfElement(WebElement element,String elementName){
        try {
            log.info("Waiting for Visibility of element: {}",elementName);
            return wait.until(ExpectedConditions.visibilityOf(element));
        } catch (StaleElementReferenceException e) {
            log.error("Element '{}' went stale cannot check for visibility.",elementName);
            throw new FrameworkException("Element "+elementName+" went stale.",e);
        }catch (TimeoutException e){
            log.error("⏳ Timeout: '{}' element remains invisible",elementName);
            throw new FrameworkException("Timeout waiting for visibility of: "+elementName,e);
        }catch (Exception e) {
            log.error("⚠ Unexpected error during wait for visibility of '{}'",elementName, e);
            throw new FrameworkException("Unexpected wait error for visibility of: " + elementName, e);
        }
    }

    protected boolean waitForInvisibilityOfElement(By locator,String elementName){
        try {
            log.info("Waiting for invisibility of element: {} by locator {}",elementName,locator);
            return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        }  catch (StaleElementReferenceException e) {
            log.error("Element '{}' went stale cannot check for invisibility by locator {}",elementName,locator);
            throw new FrameworkException("Element "+elementName+" went stale",e);
        }catch (TimeoutException e){
            log.error("⏳ Timeout: '{}' element remains visible, using locator {}",elementName,locator);
            throw new FrameworkException("Timeout waiting for invisibility of: "+elementName,e);
        }catch (NoSuchElementException e){
            log.error("No such element: '{}' found with locator {}",elementName,locator,e);
            throw new FrameworkException("No such element: "+elementName+" found",e);
        }catch (Exception e) {
            log.error("⚠ Unexpected error during wait for invisibility of '{}'",elementName, e);
            throw new FrameworkException("Unexpected wait error for invisibility of: " + elementName, e);
        }
    }

    protected boolean waitForInvisibilityOfElement(WebElement element,String elementName){
        try {
            log.info("Waiting for invisibility of element: {}",elementName);
            return wait.until(ExpectedConditions.invisibilityOf(element));
        } catch (StaleElementReferenceException e) {
            log.error("Element '{}' went stale cannot check for invisibility.",elementName);
            throw new FrameworkException("Element "+elementName+" went stale.",e);
        }catch (TimeoutException e){
            log.error("⏳ Timeout: '{}' element remains visible",elementName);
            throw new FrameworkException("Timeout waiting for invisibility of: "+elementName,e);
        }catch (Exception e) {
            log.error("⚠ Unexpected error during wait for invisibility of '{}'",elementName, e);
            throw new FrameworkException("Unexpected wait error for invisibility of: " + elementName, e);
        }
    }

    protected WebElement waitForElementToBeClickable(By locator, String elementName){
        try {
            log.info("Waiting for element to be clickable: {}", elementName);
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (StaleElementReferenceException e) {
            log.error("Element '{}' went stale cannot check if clickable by locator {}",elementName,locator);
            throw new FrameworkException("Element '"+elementName+"' went stale",e);
        }catch (TimeoutException e){
            log.error("⏳ Timeout: '{}' element is not clickable, using locator {}",elementName,locator);
            throw new FrameworkException("Timeout waiting for '"+elementName+"' to be clickable",e);
        }catch (NoSuchElementException e){
            log.error("No such element: '{}' found with locator {}",elementName,locator,e);
            throw new FrameworkException("No such element: "+elementName+" found",e);
        }catch (Exception e) {
            log.error("⚠ Unexpected error during wait for '{}' to be clickable",elementName, e);
            throw new FrameworkException("Unexpected wait error for "+elementName+" to be clickable", e);
        }

    }

    protected WebElement waitForElementToBeClickable(WebElement element, String elementName){
        try {
            log.info("Waiting for element to be clickable: {}", elementName);
            return wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (StaleElementReferenceException e) {
            log.error("Element '{}' went stale cannot check if clickable.",elementName);
            throw new FrameworkException("Element "+elementName+" went stale.",e);
        }catch (TimeoutException e){
            log.error("⏳ Timeout: '{}' element is not clickable",elementName);
            throw new FrameworkException("Timeout waiting for "+elementName+" to be clickable"+elementName,e);
        }catch (Exception e) {
            log.error("⚠ Unexpected error during wait for '{}' to be clickable",elementName, e);
            throw new FrameworkException("Unexpected wait error for "+elementName+" to be clickable", e);
        }

    }

    /************* Click action methods *************/

    protected void clickOnElement(By locator,String elementName){

        waitForElementToBeClickable(locator,elementName);

        try {
            log.info("Clicking on element: {}", elementName);
            driver.findElement(locator).click();
        } catch (StaleElementReferenceException e) {
            log.error("Element '{}' went stale cannot check if clickable by locator {}",elementName,locator);
            throw new FrameworkException("Element '"+elementName+"' went stale",e);
        } catch (NoSuchElementException e){
            log.error("Element '{}' not found using locator: {}",elementName,locator);
            throw new FrameworkException("Element "+elementName+" not found.",e);
        } catch (ElementClickInterceptedException e){
            log.error("❌ Element '{}' could not be clicked (intercepted). Locator: {}", elementName, locator);
            throw new FrameworkException("Click intercepted on: "+elementName,e);
        } catch (Exception e){
            log.error("❌ Unexpected error clicking on '{}' with locator {}",elementName,locator);
            throw new FrameworkException("Unexpected click error on: "+elementName,e);
        }
    }

    protected void clickOnElement(WebElement element,String elementName) {

        waitForElementToBeClickable(element, elementName);

        try {
            log.info("Clicking on element: {}", elementName);
            element.click();
        } catch (StaleElementReferenceException e) {
            log.error("Element '{}' went stale cannot check if clickable",elementName);
            throw new FrameworkException("Element '"+elementName+"' went stale",e);
        } catch (ElementClickInterceptedException e){
            log.error("❌ Element '{}' could not be clicked (intercepted)", elementName);
            throw new FrameworkException("Click intercepted on: "+elementName,e);
        } catch (Exception e){
            log.error("❌ Unexpected error clicking on '{}'",elementName,e);
            throw new FrameworkException("Unexpected click error on: "+elementName,e);
        }
    }

    protected void javascriptClick(By locator,String elementName){
        WebElement element = waitForElementToBeClickable(locator,elementName);

        try {
            log.info("JavaScript Executor clicking on element: {}", elementName);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();",element);
            log.info("JavaScript Executor clicked on element: {}",elementName);
        } catch (JavascriptException e) {
            log.error("Element '{}' was not clickable by Javascript Executor by locator {}",elementName,locator);
            throw new FrameworkException("Element '"+elementName+"' was not clickable by Javascript Executor",e);
        }catch (StaleElementReferenceException e) {
            log.error("Element '{}' went stale cannot check if clickable by locator {}",elementName,locator);
            throw new FrameworkException("Element '"+elementName+"' went stale",e);
        } catch (NoSuchElementException e){
            log.error("Element '{}' not found using locator: {}",elementName,locator);
            throw new FrameworkException("Element "+elementName+" not found.",e);
        }catch (Exception e){
            log.error("❌ Unexpected error clicking on '{}' with locator {}",elementName,locator);
            throw new FrameworkException("Unexpected click error on: "+elementName,e);
        }
    }

    protected void javascriptClick(WebElement element,String elementName){

        waitForElementToBeClickable(element,elementName);

        try {
            log.info("JavaScript Executor clicking on element: {}", elementName);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();",element);
            log.info("JavaScript Executor clicked on element: {}",elementName);
        } catch (JavascriptException e) {
            log.error("Element '{}' was not clickable by Javascript Executor",elementName);
            throw new FrameworkException("Element '"+elementName+"' was not clickable by Javascript Executor",e);
        }catch (StaleElementReferenceException e) {
            log.error("Element '{}' went stale cannot check if clickable",elementName);
            throw new FrameworkException("Element '"+elementName+"' went stale",e);
        }catch (Exception e){
            log.error("❌ Unexpected error clicking on '{}' with locator {}",elementName);
            throw new FrameworkException("Unexpected click error on: "+elementName,e);
        }
    }



    /************* Get and set values methods *************/

    protected String getText(By locator,String elementName){
        waitForVisibilityOfElement(locator,elementName);

        try {
            log.info("Getting text from element: {}",elementName);
            return driver.findElement(locator).getText();
        } catch (StaleElementReferenceException e) {
            log.error("Element '{}' went stale cannot interact by locator {}",elementName,locator);
            throw new FrameworkException("Element '"+elementName+"' went stale",e);
        } catch (NoSuchElementException e){
            log.error("Element '{}' not found using locator: {}",elementName,locator);
            throw new FrameworkException("Element "+elementName+" not found.",e);
        } catch (Exception e){
            log.error("❌ Unexpected error on getText of element '{}' with locator {}",elementName,locator);
            throw new FrameworkException("Unexpected getText error on: "+elementName,e);
        }
    }

    protected String getText(WebElement element,String elementName){
        waitForVisibilityOfElement(element,elementName);

        try {
            log.info("Getting text from element: {}",elementName);
            return element.getText();
        } catch (StaleElementReferenceException e) {
            log.error("Element '{}' went stale cannot interact",elementName);
            throw new FrameworkException("Element "+elementName+" went stale",e);
        }catch (Exception e){
            log.error("❌ Unexpected error on getText of element '{}'",elementName);
            throw new FrameworkException("Unexpected getText error on: "+elementName,e);
        }
    }

    protected void enterText(By locator,String elementName,String textToEnter){
        waitForElementToBeClickable(locator,elementName);

        try {
            log.info("Entering text in the element: {}",elementName);
            WebElement element = driver.findElement(locator);

            element.clear();
            element.sendKeys(textToEnter);
        } catch (StaleElementReferenceException e) {
            log.error("Element '{}' went stale cannot interact by locator {}",elementName,locator);
            throw new FrameworkException("Element '"+elementName+"' went stale",e);
        } catch (NoSuchElementException e){
            log.error("Element '{}' not found using locator: {}",elementName,locator);
            throw new FrameworkException("Element "+elementName+" not found.",e);
        }catch (Exception e) {
            log.error("❌ Unexpected error while entering text in element '{}' with locator {}",elementName,locator);
            throw new FrameworkException("",e);
        }
    }

    protected void enterText(WebElement element,String elementName,String textToEnter){
        waitForElementToBeClickable(element,elementName);
        try {
            log.info("Entering text in the element: {}",elementName);
            element.clear();
            element.sendKeys(textToEnter);
        } catch (StaleElementReferenceException e) {
            log.error("Element '{}' went stale cannot interact",elementName);
            throw new FrameworkException("Element "+elementName+" went stale",e);
        }catch (Exception e){
            log.error("❌ Unexpected error while entering text in element '{}'",elementName);
            throw new FrameworkException("Unexpected error while entering text: "+elementName,e);
        }
    }

    protected String getAttribute(By locator,String elementName, String attribute){
        waitForVisibilityOfElement(locator,elementName);
        try {
            log.info("Getting attribute: {} from element: {}",attribute,elementName);
            return driver.findElement(locator).getAttribute(attribute);
        } catch (StaleElementReferenceException e) {
            log.error("Element '{}' went stale cannot interact by locator {}",elementName,locator);
            throw new FrameworkException("Element '"+elementName+"' went stale",e);
        } catch (NoSuchElementException e){
            log.error("Element '{}' not found using locator: {}",elementName,locator);
            throw new FrameworkException("Element "+elementName+" not found.",e);
        }catch (Exception e) {
            log.error("❌ Unexpected error while getting attribute {} element '{}' with locator {}",attribute,elementName,locator);
            throw new FrameworkException("",e);
        }
    }

    protected String getAttribute(WebElement element,String elementName, String attribute){
        waitForVisibilityOfElement(element,elementName);
        try {
            log.info("Getting attribute: {} from element: {}",attribute,elementName);
            return element.getAttribute(attribute);
        } catch (StaleElementReferenceException e) {
            log.error("Element '{}' went stale cannot interact",elementName);
            throw new FrameworkException("Element "+elementName+" went stale",e);
        }catch (Exception e){
            log.error("❌ Unexpected error while getting attribute {} of element '{}'",attribute,elementName);
            throw new FrameworkException("Unexpected error while entering text: "+elementName,e);
        }
    }

    /************* miscellaneous action methods *************/

    protected void scrollIntoView(By locator,String elementName) {
        try {
            log.info("Scrolling into view: {}", elementName);
            WebElement element = driver.findElement(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        } catch (JavascriptException e) {
            log.error("Element '{}' could not be scrolled into view by Javascript Executor by locator {}",elementName,locator);
            throw new FrameworkException("Element '"+elementName+"' could not be scrolled into view by Javascript Executor",e);
        }catch (StaleElementReferenceException e) {
            log.error("Element '{}' went stale cannot scroll into view by locator {}",elementName,locator);
            throw new FrameworkException("Element '"+elementName+"' went stale",e);
        } catch (NoSuchElementException e){
            log.error("Element '{}' not found using locator: {}",elementName,locator);
            throw new FrameworkException("Element "+elementName+" not found.",e);
        }catch (Exception e){
            log.error("❌ Unexpected error while scrolling into view '{}' with locator {}",elementName,locator);
            throw new FrameworkException("Unexpected error on: "+elementName,e);
        }
    }

    protected void scrollIntoView(WebElement element,String elementName) {
        try {
            log.info("Scrolling into view: {}", elementName);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        } catch (JavascriptException e) {
            log.error("Element '{}' could not be scrolled into view by Javascript Executor",elementName);
            throw new FrameworkException("Element '"+elementName+"' could not be scrolled into view by Javascript Executor",e);
        }catch (StaleElementReferenceException e) {
            log.error("Element '{}' went stale cannot scroll into view",elementName);
            throw new FrameworkException("Element '"+elementName+"' went stale",e);
        }catch (Exception e){
            log.error("❌ Unexpected error while scrolling into view '{}'}",elementName);
            throw new FrameworkException("Unexpected error on: "+elementName,e);
        }
    }





}
