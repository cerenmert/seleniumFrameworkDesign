package pages;

import abstractComponents.AbstractComponents;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.Optional;

public class CheckOutPage extends AbstractComponents {

    WebDriver driver;

    public CheckOutPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "[placeholder='Select Country']")
    WebElement countryInput;

    By countryListBy = By.cssSelector(".ta-results");

    @FindBy(css = ".ta-results .ng-star-inserted")
    List<WebElement> countryOptions;

    @FindBy(css = ".action__submit")
    WebElement placeOrderBtn;

    @FindBy(css = ".payment__types")
    WebElement paymentMethodButtons;

    public void sendKeyToCountrySelection(String keyword) {
        Actions actions = new Actions(driver);
        actions.sendKeys((countryInput), keyword).build().perform();
    }

    public void waitForCountryListToAppear() {
        waitForElementToAppear(countryListBy);
    }

    public List<WebElement> getTheCountryListAfterSendingKey() {
        return countryOptions;
    }

    public Optional<WebElement> getOneCountryOption(String countryName) {
        return getTheCountryListAfterSendingKey()
                .stream()
                .filter(country -> country.getText().equalsIgnoreCase(countryName))
                .findFirst();
    }

    public void selectCountry(String countryName) {
        if (getOneCountryOption(countryName).isPresent()) {
            WebElement countryToClick = getOneCountryOption(countryName).get();
            System.out.println("Found and will be clicking: " + countryToClick.getText());
            countryToClick.click();
        } else {
            System.err.println("Error: 'Turkey' was not found in the list.");
        }
    }

    public ConfirmationPage submitOrder() {
        placeOrderBtn.click();
        return new ConfirmationPage(driver);
    }

    public boolean checkPaymentMethodsDisplayed() {
        return paymentMethodButtons.isDisplayed();
    }

}
