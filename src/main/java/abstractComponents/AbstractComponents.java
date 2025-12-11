package abstractComponents;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.CartPage;
import pages.OrderHistoryPage;

import java.time.Duration;

public class AbstractComponents {

    WebDriver driver;

    public AbstractComponents(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "[routerlink*='cart']")
    WebElement cartHeader;

    @FindBy(css = "[routerlink*='myorders']")
    WebElement ordersHeader;

    public void waitForElementToAppear(By findBy) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
    }

    public void waitForElementToDisappear(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public CartPage goToCartPage() {
        cartHeader.click();
        return new CartPage(driver);
    }

    public OrderHistoryPage goToOrdersPage() {
        ordersHeader.click();
        return new OrderHistoryPage(driver);
    }


}
