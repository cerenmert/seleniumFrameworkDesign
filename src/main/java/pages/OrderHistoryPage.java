package pages;

import abstractComponents.AbstractComponents;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class OrderHistoryPage extends AbstractComponents {

    WebDriver driver;

    public OrderHistoryPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
