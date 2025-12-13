package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CheckOutPage;
import pages.ConfirmationPage;
import pages.ProductCatalogue;
import testComponents.BaseTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class SubmitOrderTest3 extends BaseTest {
    String confirmationText = "THANKYOU FOR THE ORDER.";

    @Test(dataProvider = "getData")
    public void submitOrder(HashMap<String, String> input) {
        ProductCatalogue productCatalogue = landingPage.loginToApp(input.get("email"), input.get("password"));
        productCatalogue.addProductToCart(input.get("productName"));
        CartPage cartPage = productCatalogue.goToCartPage();
        Boolean match = cartPage.checkProductNameInCart(input.get("productName"));
        Assert.assertTrue(match);
        CheckOutPage checkOutPage = cartPage.goToCheckoutPage();
        Assert.assertTrue(checkOutPage.checkPaymentMethodsDisplayed());
        checkOutPage.sendKeyToCountrySelection(input.get("countryKey"));
        checkOutPage.waitForCountryListToAppear();
        Assert.assertNotEquals(checkOutPage.getTheCountryListAfterSendingKey(), null);
        checkOutPage.getOneCountryOption(input.get("countryName"));
        checkOutPage.selectCountry(input.get("countryName"));
        ConfirmationPage confirmationPage = checkOutPage.submitOrder();
        Assert.assertEquals(confirmationPage.getOrderConfirmationText(), confirmationText);
    }

    @DataProvider
    public Object[][] getData() throws IOException {
        List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir") + "//src//test//java//data//SubmitOrder.json");
        return new Object[][]{{data.get(0)}, {data.get(1)}};
    }

}