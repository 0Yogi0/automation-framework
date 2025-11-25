package uiTestFramework.PageObjects;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import uiTestFramework.Utilities.FrameworkException;

import java.time.Duration;
import java.util.concurrent.Callable;

public abstract class BasePageV2 {

    private static final int DEFAULT_TIMEOUT = 10;

    protected Logger log;
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePageV2(WebDriver driver, Logger logger) {
        this.driver = driver;
        this.log = logger;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
    }

    /* ============================================================
                      UTILITY HELPERS
       ============================================================ */

    private JavascriptExecutor getJsExecutor() {
        return (JavascriptExecutor) driver;
    }

    // Optional element highlighting — VERY useful for debug
    private void highlight(WebElement element) {
        try {
            getJsExecutor().executeScript("arguments[0].style.border='2px solid orange'", element);
        } catch (Exception ignored) {}
    }

    /**
     * Centralized wrapper for waits (visibility/clickable)
     */
    private <T> T safeWait(String action, String elementName, Callable<T> waitLogic) {
        try {
            log.info("{}: {}", action, elementName);
            return waitLogic.call();

        } catch (StaleElementReferenceException e) {
            log.error("❌ StaleElementReference: '{}' became stale during {}", elementName, action);
            throw new FrameworkException("Element stale: " + elementName, e);

        } catch (TimeoutException e) {
            log.error("⏳ Timeout: '{}' did not satisfy '{}'", elementName, action);
            throw new FrameworkException("Timeout waiting for: " + elementName, e);

        } catch (NoSuchElementException e) {
            log.error("❌ No such element '{}' during {}", elementName, action);
            throw new FrameworkException("Element not found: " + elementName, e);

        } catch (Exception e) {
            log.error("⚠ Unexpected error during {} on '{}': {}", action, elementName, e.getMessage());
            throw new FrameworkException("Unexpected error: " + elementName, e);
        }
    }

    /**
     * Centralized wrapper for click, text, attribute, JS actions
     */
    private void safeAction(String action, String elementName, Callable<Void> actionLogic) {
        try {
            log.info("{} on '{}'", action, elementName);
            actionLogic.call();
            log.info("✔ {} successful on '{}'", action, elementName);

        } catch (ElementClickInterceptedException e) {
            log.error("❌ Click intercepted on '{}'", elementName);
            throw new FrameworkException("Click intercepted: " + elementName, e);

        } catch (InvalidElementStateException e) {
            log.error("❌ Invalid element state: '{}'", elementName);
            throw new FrameworkException("Invalid state for: " + elementName, e);

        } catch (StaleElementReferenceException e) {
            log.error("❌ Stale element '{}'", elementName);
            throw new FrameworkException("Element stale: " + elementName, e);

        } catch (NoSuchElementException e) {
            log.error("❌ Element '{}' not found", elementName);
            throw new FrameworkException("Element not found: " + elementName, e);

        } catch (JavascriptException e) {
            log.error("❌ JavaScript failure on '{}'", elementName);
            throw new FrameworkException("JS failure: " + elementName, e);

        } catch (Exception e) {
            log.error("⚠ Unexpected error performing {} on '{}': {}", action, elementName, e.getMessage());
            throw new FrameworkException("Unexpected error on: " + elementName, e);
        }
    }


    /* ============================================================
                         WAIT METHODS
       ============================================================ */

    protected WebElement waitForVisibilityOfElement(By locator, String elementName) {
        return safeWait("Wait for visibility", elementName,
                () -> wait.until(ExpectedConditions.visibilityOfElementLocated(locator)));
    }

    protected WebElement waitForVisibilityOfElement(WebElement element, String elementName) {
        return safeWait("Wait for visibility", elementName,
                () -> wait.until(ExpectedConditions.visibilityOf(element)));
    }

    protected boolean waitForInvisibilityOfElement(By locator, String elementName) {
        return safeWait("Wait for invisibility", elementName,
                () -> wait.until(ExpectedConditions.invisibilityOfElementLocated(locator)));
    }

    protected boolean waitForInvisibilityOfElement(WebElement element, String elementName) {
        return safeWait("Wait for invisibility", elementName,
                () -> wait.until(ExpectedConditions.invisibilityOf(element)));
    }

    protected WebElement waitForElementToBeClickable(By locator, String elementName) {
        return safeWait("Wait for clickable", elementName,
                () -> wait.until(ExpectedConditions.elementToBeClickable(locator)));
    }

    protected WebElement waitForElementToBeClickable(WebElement element, String elementName) {
        return safeWait("Wait for clickable", elementName,
                () -> wait.until(ExpectedConditions.elementToBeClickable(element)));
    }


    /* ============================================================
                         CLICK METHODS
       ============================================================ */

    protected void clickOnElement(By locator, String elementName) {
        WebElement element = waitForElementToBeClickable(locator, elementName);

        safeAction("Click", elementName, () -> {
            highlight(element);
            element.click();
            return null;
        });
    }

    protected void clickOnElement(WebElement element, String elementName) {
        waitForElementToBeClickable(element, elementName);

        safeAction("Click", elementName, () -> {
            highlight(element);
            element.click();
            return null;
        });
    }


    /* ============================================================
                     JAVASCRIPT CLICK METHODS
       ============================================================ */

    protected void javascriptClick(By locator, String elementName) {
        WebElement element = waitForElementToBeClickable(locator, elementName);

        safeAction("JS Click", elementName, () -> {
            highlight(element);
            getJsExecutor().executeScript("arguments[0].click();", element);
            return null;
        });
    }

    protected void javascriptClick(WebElement element, String elementName) {
        waitForElementToBeClickable(element, elementName);

        safeAction("JS Click", elementName, () -> {
            highlight(element);
            getJsExecutor().executeScript("arguments[0].click();", element);
            return null;
        });
    }


    /* ============================================================
                     TEXT & ATTRIBUTE METHODS
       ============================================================ */

    protected String getText(By locator, String elementName) {
        WebElement element = waitForVisibilityOfElement(locator, elementName);

        return safeWait("Get text", elementName,
                element::getText);
    }

    protected String getText(WebElement element, String elementName) {
        waitForVisibilityOfElement(element, elementName);

        return safeWait("Get text", elementName,
                element::getText);
    }

    protected void enterText(By locator, String elementName, String textToEnter) {
        WebElement element = waitForElementToBeClickable(locator, elementName);

        safeAction("Enter text", elementName, () -> {
            element.clear();
            element.sendKeys(textToEnter);
            return null;
        });
    }

    protected void enterText(WebElement element, String elementName, String textToEnter) {
        waitForElementToBeClickable(element, elementName);

        safeAction("Enter text", elementName, () -> {
            element.clear();
            element.sendKeys(textToEnter);
            return null;
        });
    }

    protected String getAttribute(By locator, String elementName, String attribute) {
        WebElement element = waitForVisibilityOfElement(locator, elementName);

        return safeWait("Get attribute '" + attribute + "'", elementName,
                () -> element.getAttribute(attribute));
    }

    protected String getAttribute(WebElement element, String elementName, String attribute) {
        waitForVisibilityOfElement(element, elementName);

        return safeWait("Get attribute '" + attribute + "'", elementName,
                () -> element.getAttribute(attribute));
    }


    /* ============================================================
                         SCROLL INTO VIEW
       ============================================================ */

    protected void scrollIntoView(By locator, String elementName) {
        WebElement element = waitForVisibilityOfElement(locator, elementName);

        safeAction("Scroll into view", elementName, () -> {
            getJsExecutor().executeScript("arguments[0].scrollIntoView(true);", element);
            return null;
        });
    }

    protected void scrollIntoView(WebElement element, String elementName) {
        waitForVisibilityOfElement(element, elementName);

        safeAction("Scroll into view", elementName, () -> {
            getJsExecutor().executeScript("arguments[0].scrollIntoView(true);", element);
            return null;
        });
    }

}
