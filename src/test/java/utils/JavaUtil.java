package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class JavaUtil {

    public void flash(By locator) {
        WebElement webElement=Driver.getDriver().findElement(locator);
        String bgcolor = webElement.getCssValue("backgroundColor");
        for (int i = 0; i < 4; i++) {
            changeColor(webElement,"rgb(0,200,0)");// 1
            changeColor(webElement, bgcolor);//
        }
    }

    public void changeColor(WebElement element, String color) {
        JavascriptExecutor js = ((JavascriptExecutor) Driver.getDriver());
        js.executeScript("arguments[0].style.backgroundColor = '" + color + "'", element);
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
        }
    }

    public  void drawBorder(By locator) {
        JavascriptExecutor js = ((JavascriptExecutor) Driver.getDriver());
        WebElement webElement=Driver.getDriver().findElement(locator);
        js.executeScript("arguments[0].style.border='2px solid red'", webElement);
    }

    public  void generateAlert(String message) {
        JavascriptExecutor js = ((JavascriptExecutor) Driver.getDriver());
        js.executeScript("alert('" + message + "')");
    }

    public  void clickOn(By locator) {
        JavascriptExecutor js = ((JavascriptExecutor) Driver.getDriver());
        WebElement webElement=Driver.getDriver().findElement(locator);
        js.executeScript("arguments[0].click();", webElement);
    }

    public void clickWith(By locator) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(),
                Long.parseLong(ConfigReader.getProperty("timeout")));
        WebElement webElement=Driver.getDriver().findElement(locator);
        wait.until(ExpectedConditions.elementToBeClickable(webElement));
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView(true);", webElement);
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", webElement);
    }

    public   void refreshBrowser() {
        JavascriptExecutor js = ((JavascriptExecutor) Driver.getDriver());
        js.executeScript("history.go(0)");
    }

    public String getTitle() {
        JavascriptExecutor js = ((JavascriptExecutor) Driver.getDriver());
        String title = js.executeScript("return document.title;").toString();
        return title;
    }

    public  String getPageInnerText() {
        JavascriptExecutor js = ((JavascriptExecutor) Driver.getDriver());
        String pageText = js.executeScript("return document.documentElement.innerText;").toString();
        return pageText;
    }

    public  void scrollPageDown() {
        JavascriptExecutor js = ((JavascriptExecutor) Driver.getDriver());
        js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
    }

    public void scrollByElement(By locator){
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        WebElement webElement=Driver.getDriver().findElement(locator);
        js.executeScript("arguments[0].scrollIntoView();", webElement);
    }

    public void scrollByPixel(String xPixel, String yPixel){
        String string = "window.scrollBy(" + xPixel +"," + yPixel + ")";
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript(string);
    }

    public String getBrowserInfo(){
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        String uAgent = js.executeScript("return navigator.userAgent;").toString();
        return uAgent;
    }

    public void setAttributeValue(By locator, String atribute, String value){
        JavascriptExecutor js = ((JavascriptExecutor) Driver.getDriver());
        WebElement webElement=Driver.getDriver().findElement(locator);
        js.executeScript("arguments[0].setAttribute('"+atribute+"', '"+value+"')",webElement);
    }

}
