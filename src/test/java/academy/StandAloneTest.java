package academy;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import java.time.Duration;

public class StandAloneTest {
    public static void main(String[] args) throws InterruptedException {
        String productName = "ZARA COAT 3";
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        LandingPage landingPage = new LandingPage(driver);
        landingPage.navigateToMainPage();
        ProductCatalogue productCatalogue = landingPage.loginToApp("ceren1481@gmail.com", "Crnmert1481:");
        productCatalogue.getProductList();
        productCatalogue.getProductByName(productName);
        productCatalogue.addProductToCart(productName);
        CartPage cartPage = productCatalogue.goToCartPage();
        Boolean match = cartPage.checkProductNameInCart(productName);
        Assert.assertTrue(match);
        CheckOutPage checkOutPage = cartPage.goToCheckoutPage();
        checkOutPage.sendKeyToCountrySelection("tur");
        checkOutPage.waitForCountryListToAppear();
        Assert.assertNotEquals(checkOutPage.getTheCountryListAfterSendingKey(), null);
        checkOutPage.getOneCountryOption("Turkey");
        checkOutPage.selectCountry("Turkey");
        ConfirmationPage confirmationPage = checkOutPage.submitOrder();
        Assert.assertEquals(confirmationPage.getOrderConfirmationText(), "THANKYOU FOR THE ORDER.");
        Thread.sleep(3000);
        driver.close();
    }
}
