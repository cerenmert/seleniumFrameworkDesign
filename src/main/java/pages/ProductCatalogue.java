package pages;

import abstractComponents.AbstractComponents;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductCatalogue extends AbstractComponents {

    WebDriver driver;

    public ProductCatalogue(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".mb-3")
    List<WebElement> allProducts;

    @FindBy(css = "div[class*='ngx-spinner-overlay']")
    WebElement spinner;

    By productsBy = By.cssSelector(".mb-3");
    By addToCartBy = By.cssSelector(".card-body button:last-of-type");
    By toastMessageBy = By.cssSelector("#toast-container");
    By animatingBy = By.cssSelector("div[class*='ng-animating']");

    public List<WebElement> getProductList() {
        waitForElementToAppear(productsBy);
        return allProducts;
    }

    public WebElement getProductByName(String productName) {
        return getProductList()
                .stream()
                .filter(product -> product.findElement(By.cssSelector("b")).getText().equals(productName))
                .findFirst().orElse(null);
    }

    public void addProductToCart(String productName) {
        WebElement product = getProductByName(productName);
        product.findElement(addToCartBy).click();
        waitForElementToAppear(toastMessageBy);
        waitForElementToAppear(animatingBy);
        waitForElementToDisappear(spinner);

    }
}
