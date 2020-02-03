package stepdefinitions;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import utils.ConfigReader;
import utils.Driver;
import java.util.concurrent.TimeUnit;


public class SuiteSetupAndTearDown {
    @Before("@web")
    public void suiteSetup(Scenario scenario) {
        Driver.initializeDriver();
        Driver.getDriver().manage().deleteAllCookies();
        Driver.getDriver().manage().window().maximize();
        Driver.getDriver().manage().timeouts().implicitlyWait(Integer.valueOf(ConfigReader.getProperty("timeout")), TimeUnit.SECONDS);
        System.out.println("=======================================");
        System.out.println("Test is starting: "+ scenario.getName());
    }

    @After("@web")
    public void suiteTearDown(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                TakesScreenshot takesScreenshot = (TakesScreenshot) Driver.getDriver();
                byte[] image = takesScreenshot.getScreenshotAs(OutputType.BYTES);
                scenario.embed(image, "image/png");
            }
        } catch (WebDriverException e) {
            System.out.println("WebDriverException found");
        }
        System.out.println(scenario.getName() + " is passed");
        System.out.println("Test result is "+ scenario.getStatus());
        System.out.println("Tag Name is "+ scenario.getSourceTagNames());
        Driver.closeDriver();
        System.out.println("=======================================");
    }
}
