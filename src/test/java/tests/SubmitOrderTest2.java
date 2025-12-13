package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CheckOutPage;
import pages.ConfirmationPage;
import pages.ProductCatalogue;
import testComponents.BaseTest;

import java.util.HashMap;

public class SubmitOrderTest2 extends BaseTest {
    String confirmationText = "THANKYOU FOR THE ORDER.";

    // Here, integrated data provider with HashMap to send multiple data as one Hash object
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

    // Data provider method returning HashMap objects
    // Test data is encapsulated within HashMap for better organization
    // Test will run for each HashMap provided, in this case two Hashmap objects are provided
    @DataProvider
    public Object[][] getData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("email", "ceren1481@gmail.com");
        map.put("password", "1234");
        map.put("productName", "ZARA COAT 3");
        map.put("countryKey", "tur");
        map.put("countryName", "Turkey");

        HashMap<String, String> map1 = new HashMap<>();
        map1.put("email", "test@gmail.com");
        map1.put("password", "1234");
        map1.put("productName", "ADIDAS ORIGINAL");
        map1.put("countryKey", "ind");
        map1.put("countryName", "India");

        return new Object[][]{{map}, {map1}};
    }


}