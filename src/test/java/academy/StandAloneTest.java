package academy;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public class StandAloneTest {
    public static void main(String[] args) throws InterruptedException {
        String productName = "ZARA COAT 3";
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://rahulshettyacademy.com/client");
        driver.findElement(By.id("userEmail")).sendKeys("ceren1481@gmail.com");
        driver.findElement(By.id("userPassword")).sendKeys("Crnmert1481:");
        driver.findElement(By.id("login")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
        List<WebElement> allProducts = driver.findElements(By.cssSelector(".mb-3"));
        WebElement p = allProducts.stream()
                .filter(product -> product.findElement(By.cssSelector("b")).getText().equals(productName))
                .findFirst().orElse(null);
        //".card-body button:last-of-type" --> add to cart buttons
        p.findElement(By.cssSelector(".card-body button:last-of-type")).click();
        // tost-container is shown after clicking the add to cart button
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("div[class*='ng-animating']"))));
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector("div[class*='ngx-spinner-overlay']"))));
        // go to cart page by clicking the cart menu button
        driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
        List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
        Boolean matched = cartProducts.stream().anyMatch(cartP -> cartP.getText().equalsIgnoreCase(productName));
        if (matched.equals(true)) {
            System.out.println("Product " +productName+ " found in the cart ");
        } else {
            System.out.println("Product " +productName+ " NOT found in the cart ");
        }
        driver.findElement(By.cssSelector(".totalRow button")).click();
        Actions actions = new Actions(driver);
        actions.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "tur").build().perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
        //Several ways to select the Turkey from the country list after writing "tur"
        //driver.findElement(By.cssSelector(".ta-results button:first-of-type")).click();
        //driver.findElement(By.cssSelector(".ta-item:nth-of-type(1)")).click();
        //driver.findElement(By.cssSelector("//button[contains(@class,'ta-item')][1]")).click();
        List<WebElement> countries = driver.findElements(By.cssSelector(".ta-results button"));
        // Use Stream API to find the country "Turkey"
        Optional<WebElement> targetCountry = countries.stream()
                .filter(country -> country.getText().equalsIgnoreCase("Turkey"))
                .findFirst();
        // Check if the element was found and then click it
        if (targetCountry.isPresent()) {
            WebElement countryToClick = targetCountry.get();
            System.out.println("Found and will be clicking: " + countryToClick.getText());
            countryToClick.click();
        } else {
            System.err.println("Error: 'Turkey' was not found in the list.");
        }
        driver.findElement(By.cssSelector(".action__submit")).click();
        String text = driver.findElement(By.cssSelector(".hero-primary")).getText();
        Assert.assertEquals(text, "THANKYOU FOR THE ORDER.");

        Thread.sleep(3000);
        driver.close();
    }
}
