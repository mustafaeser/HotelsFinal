package utils;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.List;
import java.util.function.Function;

public class ElementUtil {

    // This is the most common wait function used in selenium
    public static WebElement webAction(final By locator) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(Driver.getDriver())
                .withTimeout(Duration.ofSeconds(15))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(ElementClickInterceptedException.class)
                .withMessage(
                        "WebDriver waited for 15 seconds but still could not find the element therefore Timeout Exception has been thrown");

        return wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(locator);
            }
        });
    }


    public String getPageTitle() {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), 20);
        return Driver.getDriver().getTitle();
    }

    public String getPageURL() {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), 20);
        return Driver.getDriver().getCurrentUrl();
    }
    public void clickOn(By locator) {
        webAction(locator).click();
    }

    public void hoverAndClickOn(By locator) {
        Actions action = new Actions(Driver.getDriver());
        action
                .moveToElement(webAction(locator))
                .click(webAction(locator))
                .build()
                .perform();
    }

    public void setValue(By locator, String value) {
        webAction(locator).clear();
        webAction(locator).sendKeys(value);
    }

    public void typeReturn(By locator){
        webAction(locator).sendKeys(Keys.RETURN);
    }

    public String getTextFromElement(By locator) {
        return webAction(locator).getText();
    }


    public String getAttributeValueFromElement(By locator, String atribute){
        return webAction(locator).getAttribute(atribute);
    }

    public String getValueFromElement(By locator){
        return webAction(locator).getAttribute("value");
    }

    public boolean isElementDisplayed(By locator) {
        return webAction(locator).isDisplayed();
    }

    public boolean isElementSelected(By locator) {
        return webAction(locator).isSelected();
    }

    public void selectFromDropdown(By locator, String dropdownText) {
        Select select = new Select(webAction(locator));
        //select element by visible text
        select.selectByVisibleText(dropdownText);
    }

    public void selectFromDropdown(By locator, int index) {
        Select select = new Select(webAction(locator));
        //select element by index
        select.selectByIndex(index);
    }

    public boolean isElementEnabled(By locator) {
        return webAction(locator).isEnabled();
    }

    public List<WebElement> webElements(By locator){
        List<WebElement> list = new ArrayList<WebElement>();
        list = Driver.getDriver().findElements(locator);
        return list;
    }

    public void clear(By locator){
        webAction(locator).clear();
    }

    public void windowMaximize(){
        Driver.getDriver().manage().window().maximize();
    }

    public void moveToElement(By locator){
        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(webAction(locator)).build().perform();
    }
    public void switchToWindowByTitle(String title) {
        Set<String> windows = Driver.getDriver().getWindowHandles();
        System.out.println("Amount of windows that are currently present :: " + windows.size());
        for (String window : windows) {
            Driver.getDriver().switchTo().window(window);
            if (Driver.getDriver().getTitle().startsWith(title)
                    || Driver.getDriver().getTitle().equalsIgnoreCase(title)) {
                break;
            } else {
                continue;
            }
        }
    }

    public void waitForPresenceOfElement(By locator) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(),
                Long.parseLong(ConfigReader.getProperty("timeout")));
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public void waitForVissibilityOfElement(By locator) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(),
                Long.parseLong(ConfigReader.getProperty("timeout")));
        wait.until(ExpectedConditions.visibilityOf(Driver.getDriver().findElement(locator)));
    }

    public void waitForElement(By locator) {
        int i = 0;
        while (i < 10) {
            try {
                Driver.getDriver().findElement(locator).isDisplayed();
                break;
            } catch (WebDriverException e) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
                i++;
            }
        }
    }

    public boolean verifyElementIsNotPresent(By locator) {
        List<WebElement> elements = Driver.getDriver().findElements(locator);
        return elements.size() == 0;
    }

    public void hitEnter() {
        Robot rb;
        try {
            rb = new Robot();
            rb.keyPress(KeyEvent.VK_ENTER);
            rb.keyRelease(KeyEvent.VK_ENTER);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean verifyAlertPresent() {
        try {
            Driver.getDriver().switchTo().alert();
            return true;
        } catch (NoAlertPresentException Ex) {
            System.out.println("Alert is not presenet");
        }
        return false;
    }

    public void takeSnapShot() {
        try {
            TakesScreenshot scrShot = ((TakesScreenshot) Driver.getDriver());
            File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd'T'HH-mm-ss-SSS").format(new Date());
            String path = System.getProperty("user.dir") + "/target/screenshots/"+timeStamp+".jpg";
            System.out.println(path);
            File DestFile = new File(path);
            FileUtils.copyFile(SrcFile, DestFile);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteOldSnapShots() throws IOException {
        File dir = new File(System.getProperty("user.dir") + "/target/screenshots/");
        for (File file : dir.listFiles()) {
            if (file.getName().endsWith(".jpg") && !file.delete()) {
                throw new IOException();
            }
        }
    }

    public void waitUntilPageLoad() {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(),
                Integer.valueOf(ConfigReader.getProperty("timeout")));
        wait.until((d) -> {
            Boolean isPageLoaded = (Boolean) ((JavascriptExecutor) Driver.getDriver())
                    .executeScript("return document.readyState").equals("complete");
            if (!isPageLoaded)
                System.out.println("Document is loading");
            return isPageLoaded;
        });
    }

    public void waitForPageToLoad(long timeOutInSeconds) {
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };
        try {
            System.out.println("Waiting for page to load...");
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), timeOutInSeconds);
            wait.until(expectation);
        } catch (Throwable error) {
            System.out.println(
                    "Timeout waiting for Page Load Request to complete after " + timeOutInSeconds + " seconds");
        }
    }

    public void waitForStaleElement(By locator) {
        int y = 0;
        while (y <= 15) {
            try {
                Driver.getDriver().findElement(locator).isDisplayed();
                break;
            } catch (StaleElementReferenceException st) {
                y++;
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (WebDriverException we) {
                y++;
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public WebElement waitForVisibility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), timeout);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForClickablility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), timeout);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public String convertDateFormat(String OriginalFormat, String TargetFormat, String Date){
        DateFormat original = new SimpleDateFormat(OriginalFormat, Locale.ENGLISH);
        DateFormat target = new SimpleDateFormat(TargetFormat);
        String formattedDate = null;
        try {
            Date date = original.parse(Date);
            formattedDate = target.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }
}