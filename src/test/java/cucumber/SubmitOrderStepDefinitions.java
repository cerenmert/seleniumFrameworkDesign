package cucumber;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.*;
import testComponents.BaseTest;


public class SubmitOrderStepDefinitions extends BaseTest {

    private ProductCatalogue productCatalogue;
    private CartPage cartPage;
    private CheckOutPage checkOutPage;
    private ConfirmationPage confirmationPage;

    @Before
    public void beforeScenario() {
        // Ensure driver is initialized (BaseTest should provide initializeDriver())
        try {
            if (this.driver == null) {
                this.driver = initializeDriver();
            }
        } catch (Throwable t) {
            // if initializeDriver is not present, let it surface during compile/run
        }
        // Ensure landingPage is initialized so steps can use it
        if (this.landingPage == null) {
            this.landingPage = new LandingPage(this.driver);
            landingPage.navigateToLandingPage();
        }
    }

    @Given ("the user is logged in with {string} and {string}")
    public void user_is_logged_in(String email, String password) {
        productCatalogue = landingPage.loginToApp(email, password);
    }

    @When ("the user adds {string} to the cart")
    public void user_adds_product_to_cart(String productName) {
        productCatalogue.addProductToCart(productName);
    }

    @And ("the user goes to the cart page")
    public void user_goes_to_cart() {
        cartPage = productCatalogue.goToCartPage();
    }

    @Then ("the cart should contain {string}")
    public void cart_should_contain_product(String productName) {
        Boolean match = cartPage.checkProductNameInCart(productName);
        Assert.assertTrue(match, "Product was not found in cart: " + productName);
    }

    @When ("the user proceeds to checkout")
    public void user_proceeds_to_checkout() {
        checkOutPage = cartPage.goToCheckoutPage();
    }

    @Then ("payment methods should be displayed")
    public void payment_methods_should_be_displayed() {
        Assert.assertTrue(checkOutPage.checkPaymentMethodsDisplayed(), "Payment methods are not displayed");
    }

    @When ("the user selects country by typing {string} and choosing {string}")
    public void user_selects_country(String countryKey, String countryName) {
        checkOutPage.sendKeyToCountrySelection(countryKey);
        checkOutPage.waitForCountryListToAppear();
        Assert.assertNotEquals(checkOutPage.getTheCountryListAfterSendingKey(), null, "Country list did not appear");
        checkOutPage.getOneCountryOption(countryName);
        checkOutPage.selectCountry(countryName);
    }

    @And ("the user submits the order")
    public void user_submits_order() {
        confirmationPage = checkOutPage.submitOrder();
    }

    @Then ("the confirmation text should be {string}")
    public void confirmation_text_should_be(String expectedText) {
        Assert.assertEquals(confirmationPage.getOrderConfirmationText(), expectedText);
    }
}
