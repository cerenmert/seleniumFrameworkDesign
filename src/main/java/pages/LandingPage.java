package pages;

import abstractComponents.AbstractComponents;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage extends AbstractComponents {
    private WebDriver driver;

    public LandingPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "login")
    WebElement submit;

    @FindBy(id = "userEmail")
    WebElement userEmail;

    @FindBy(id = "userPassword")
    WebElement userPassword;

    @FindBy (css = "[class*='toast-message']")
    WebElement errorMessageForInvalidPassword;

    public ProductCatalogue loginToApp(String email, String password) {
        userEmail.sendKeys(email);
        userPassword.sendKeys(password);
        submit.click();
        return new ProductCatalogue(driver);
    }

    public String getErrorMessageForInvalidPassword() {
        waitForWebElementToAppear(errorMessageForInvalidPassword);
        return errorMessageForInvalidPassword.getText();
    }

    public void navigateToLandingPage() {
        driver.get("https://rahulshettyacademy.com/client");
    }
}
