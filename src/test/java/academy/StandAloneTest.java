package academy;

import org.testng.annotations.Test;
import pages.*;
import org.testng.Assert;
import testComponents.BaseTest;

public class StandAloneTest extends BaseTest {
    String productName = "ZARA COAT 3";
    String countryKey = "tur";
    String countryName = "Turkey";
    String confirmationText = "THANKYOU FOR THE ORDER.";
    String email = "ceren1481@gmail.com";
    String password = "1234";

    @Test
    public void submitOrder()  {
        ProductCatalogue productCatalogue = landingPage.loginToApp(email, password);
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
    }
}
