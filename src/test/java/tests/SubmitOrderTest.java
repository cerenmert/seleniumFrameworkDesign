package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CheckOutPage;
import pages.ConfirmationPage;
import pages.ProductCatalogue;
import testComponents.BaseTest;

public class SubmitOrderTest extends BaseTest {
    String confirmationText = "THANKYOU FOR THE ORDER.";

    @Test(dataProvider = "getData")
    public void submitOrder(String email, String password, String productName, String countryKey, String countryName) {
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

    @DataProvider
    public static Object[][] getData() {
        return new Object[][]{
                {"ceren1481@gmail.com", "1234", "ZARA COAT 3", "tur", "Turkey"},
                {"test@gmail.com", "1234", "ADIDAS ORIGINAL", "ind", "India"}
        };
    }
}
