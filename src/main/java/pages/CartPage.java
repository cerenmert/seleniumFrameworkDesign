package pages;

import abstractComponents.AbstractComponents;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartPage extends AbstractComponents {

    WebDriver driver;

    public CartPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".cartSection h3")
    List<WebElement> cartProducts;

    @FindBy(css = ".totalRow button")
    WebElement checkoutButton;

    public List<WebElement> getCartProducts() {
        List<WebElement> cartProducts = this.cartProducts;
        return cartProducts;
    }

    public Boolean checkProductNameInCart(String productName) {
        Boolean matched = getCartProducts().stream().anyMatch(cartP -> cartP.getText().equalsIgnoreCase(productName));
        System.out.println("Product " + productName + (matched ? " found in the cart " : " NOT found in the cart "));
        return matched;
    }

    public CheckOutPage goToCheckoutPage() {
        checkoutButton.click();
        return new CheckOutPage(driver);
    }

}
