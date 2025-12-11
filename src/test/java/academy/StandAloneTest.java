package academy;

import org.testng.annotations.Test;
import pages.*;
import org.testng.Assert;
import testComponents.BaseTest;

import java.io.IOException;

public class StandAloneTest extends BaseTest {
    @Test
    public void submitOrder() throws InterruptedException, IOException {
        String productName = "ZARA COAT 3";
        String countryKey = "tur";
        String countryName = "Turkey";
        String confirmationText = "THANKYOU FOR THE ORDER.";
        LandingPage landingPage = launchApplication();
        ProductCatalogue productCatalogue = landingPage.loginToApp("ceren1481@gmail.com", "Crnmert1481:");
        productCatalogue.addProductToCart(productName);
        CartPage cartPage = productCatalogue.goToCartPage();
        Boolean match = cartPage.checkProductNameInCart(productName);
        Assert.assertTrue(match);
        CheckOutPage checkOutPage = cartPage.goToCheckoutPage();
        Assert.assertTrue(checkOutPage.checkPaymentMethodsDisplayed());
        checkOutPage.sendKeyToCountrySelection(countryKey);
        checkOutPage.waitForCountryListToAppear();
        Assert.assertNotEquals(checkOutPage.getTheCountryListAfterSendingKey(), null);
        checkOutPage.getOneCountryOption(countryName);
        checkOutPage.selectCountry(countryName);
        ConfirmationPage confirmationPage = checkOutPage.submitOrder();
        Assert.assertEquals(confirmationPage.getOrderConfirmationText(), confirmationText);
        Thread.sleep(1000);
        driver.close();
    }
}
