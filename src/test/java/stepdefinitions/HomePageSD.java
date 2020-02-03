package stepdefinitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import pages.HomePage;
import utils.ConfigReader;
import utils.Driver;
import utils.ElementUtil;

public class HomePageSD {

    private ElementUtil util=new ElementUtil();
    private HomePage hotelsHomePage = new HomePage();

    @Given("^I am on Hotels Home Page$")
    public void verified(){
        hotelsHomePage.openHomePage();
        Assert.assertEquals(Driver.getDriver().getCurrentUrl(), ConfigReader.getProperty("url"));
    }

    @When("^I am on default locations search result screen$")
    public void hotelsMainPage() throws InterruptedException {
        hotelsHomePage.selectADestination();
        hotelsHomePage.clickOnSearch();
    }
    @And("^I select (5 stars|4 stars|3 stars) on the hotels page$")
    public void chooseStars(String stars) throws Exception {
        int star=Integer.parseInt(stars.substring(0,1));
        hotelsHomePage.selectStar(star);
    }

    @Then("^I verify system displays only (5 stars|4 stars|3 stars) hotels on search result$")
    public void verifyStarsIsSelected(String stars) {
        switch (stars){
            case "5 stars":
                hotelsHomePage.isFiveStarSelected();
                break;
            case "4 stars":
                hotelsHomePage.isFourStarSelected();
                break;
            case "3 stars":
                hotelsHomePage.isThreeStarSelected();
        }
    }

    @Then("^I verify system displays all hotels within 10 miles radius of airport$")
    public void tenMilestoAirport() throws InterruptedException {
        System.out.println("Hotels are within 10 miles: "+hotelsHomePage.verifyHotelsAreWithin10Miles());
    }
    @And("^I verify Hilton Hotel is within 10 miles radius of downtown$")
    public void verifyHiltonPresentInTheList(){
        System.out.println("Hilton is in hotels list: "+hotelsHomePage.isHiltonPresentInTheList());
    }
}
