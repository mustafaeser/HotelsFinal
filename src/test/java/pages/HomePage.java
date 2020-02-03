package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.ConfigReader;
import utils.Driver;
import utils.ElementUtil;
import utils.JavaUtil;
import java.util.ArrayList;
import java.util.List;

public class HomePage extends Driver {
    private ElementUtil util=new ElementUtil();
    private JavaUtil jUtil=new JavaUtil();
    By searchButton = By.cssSelector("button[type='submit']");
    By destinationField=By.xpath("//input[@id='qf-0q-destination']");
    By threeStar = By.id("f-star-rating-3");
    By fourStar = By.id("f-star-rating-4");
    By fiveStar = By.id("f-star-rating-5");
    By menu=By.cssSelector("#enhanced-sort>li:nth-child(3)");
    By jfk=By.xpath("//ul[@id='sort-submenu-distance']/li /ul/li/a[contains(text(),'(JFK)')]");
    By distanceAirport = By.cssSelector("ul.property-landmarks li:first-child");
    By hotelNames = By.cssSelector("li.hotel a.property-name-link");
    public void openHomePage() {
        Driver.getDriver().navigate().to(ConfigReader.getProperty("url"));
    }

    public void selectADestination() throws InterruptedException {
        Thread.sleep(3000);
        util.setValue(destinationField,"New York, New York, United States of America");
        util.typeReturn(destinationField);
    }

    public void clickOnSearch() throws InterruptedException {
        Thread.sleep(3000);
        util.clickOn(searchButton);
    }

    public void clickOnFiveStars() throws InterruptedException {
        jUtil.scrollByPixel("0","400");
        Thread.sleep(2000);
        util.clickOn(fiveStar);
    }
    public void clickOnFourStars() throws InterruptedException {
        jUtil.scrollByPixel("0","400");
        Thread.sleep(2000);
        util.clickOn(fourStar);
    }
    public void clickOnThreeStars() throws InterruptedException {
        jUtil.scrollByPixel("0","400");
        Thread.sleep(2000);
        util.clickOn(threeStar);
    }
    public void selectStar(int star) throws Exception {
        By starSelectionBox = By.id("f-label-star-rating-" + star);
        jUtil.flash(starSelectionBox);
        util.clickOn(starSelectionBox);
        Thread.sleep(5000);
    }
    public void isFiveStarSelected(){
        System.out.println(" > 5-star checkbox is selected : " + util.isElementSelected(fiveStar));
    }
    public void isFourStarSelected(){
        System.out.println(" > 4-star checkbox is selected : " + util.isElementSelected(fourStar));
    }
    public void isThreeStarSelected(){

        System.out.println(" > 3-star checkbox is selected : " + util.isElementSelected(threeStar));
    }
    public boolean verifyHotelsAreWithin10Miles() throws InterruptedException {
        boolean isWithInTenMiles=true;
        util.clickOn(menu);
        util.clickOn(jfk);
        Thread.sleep(2000);
        jUtil.scrollByPixel("0","400");
        Thread.sleep(2000);
        jUtil.scrollByPixel("0","400");
        Thread.sleep(2000);
        List<String> distanceFromAirport = new ArrayList<>();
        List<WebElement> distances = getDriver().findElements(distanceAirport);
        System.out.print("Distances: ");
        for (WebElement distance : distances) {
            distanceFromAirport.add(distance.getText());
        }
        ArrayList<Double> distanceInNumbers = new ArrayList<Double>();
        for (int i = 0; i < distanceFromAirport.size(); i++) {
            String distArray[] = distanceFromAirport.get(i).split(" ");
            distanceInNumbers.add(Double.parseDouble(distArray[0]));
        }
        for (int i = 0; i < distanceInNumbers.size(); i++) {
            System.out.print(distanceInNumbers.get(i)+ " ");
            if (distanceInNumbers.get(i) > 10) {
                isWithInTenMiles=false;
                break;
            }
        }
        System.out.println();
        return isWithInTenMiles;
    }


    public boolean isHiltonPresentInTheList() {
        List<String> AllHotelNames = new ArrayList<>();
        List<WebElement> hotels = getDriver().findElements(hotelNames);
        for (WebElement hotel : hotels) {
            AllHotelNames.add(hotel.getText());
        }
        for (int i = 0; i < AllHotelNames.size(); i++) {
            if (AllHotelNames.get(i).contains("Hilton")) {
                System.out.println("Hotel Name : "+ AllHotelNames.get(i));
                return true;
            }
        }
        return false;
    }
}