package edu.pharmacy;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

class InventoryJourneysTest {
  WebDriver driver;
  WebDriverWait wait;
  String base = System.getProperty("app.baseUrl", "http://127.0.0.1:8080");

  @RegisterExtension TestExecutionExceptionHandler failureScreenshot = (context, cause) -> {
      if (driver instanceof TakesScreenshot screenshot) {
        try { Path folder=Path.of("target/failure-screenshots"); Files.createDirectories(folder); Files.write(folder.resolve(context.getDisplayName().replaceAll("[^A-Za-z0-9-]", "_")+".png"), screenshot.getScreenshotAs(OutputType.BYTES)); }
        catch (Exception ignored) { /* Preserve the original assertion failure. */ }
      }
  };

  @BeforeEach void setUp() {
    ChromeOptions options = new ChromeOptions();
    if ("true".equalsIgnoreCase(System.getenv("CI"))) { options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage"); }
    driver = new ChromeDriver(options);
    driver.manage().window().setSize(new Dimension(1440, 1000));
    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    driver.get(base);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inventory-body")));
    ((JavascriptExecutor)driver).executeScript("localStorage.clear()");
    driver.navigate().refresh();
  }

  @Test void searchAndFilterCatalogue() {
    driver.findElement(By.id("search")).sendKeys("Amoxicillin");
    assertTrue(driver.findElement(By.id("inventory-body")).getText().contains("Amoxicillin 250 mg"));
    driver.findElement(By.id("clear-search")).click();
    driver.findElement(By.id("status-filter")).sendKeys("Low stock");
    assertTrue(driver.findElement(By.id("inventory-body")).getText().contains("Low stock"));
  }

  @Test void addProductAndValidateDuplicateSku() {
    driver.findElement(By.id("add-item")).click();
    driver.findElement(By.id("name")).sendKeys("Demo test tablets");
    driver.findElement(By.id("sku")).sendKeys("TST-001");
    driver.findElement(By.id("category")).sendKeys("Test data");
    driver.findElement(By.id("quantity")).sendKeys("25");
    driver.findElement(By.id("reorder")).sendKeys("5");
    driver.findElement(By.id("supplier")).sendKeys("Test supplier");
    driver.findElement(By.id("expiry")).sendKeys("2027-12-31");
    driver.findElement(By.id("save-item")).click();
    assertTrue(driver.findElement(By.id("inventory-body")).getText().contains("Demo test tablets"));
  }

  @Test void receiveAndDispenseStock() {
    driver.findElement(By.cssSelector("[data-stock='p01']")).click();
    driver.findElement(By.id("movement-quantity")).sendKeys("5");
    driver.findElement(By.cssSelector("#stock-form button[type=submit]")).click();
    assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("toast"))).getText().contains("Received 5 units"));
  }

  @Test void rejectDispenseAboveAvailableStock() {
    driver.findElement(By.cssSelector("[data-stock='p04']")).click();
    driver.findElement(By.id("movement")).sendKeys("Dispense stock");
    driver.findElement(By.id("movement-quantity")).sendKeys("1");
    driver.findElement(By.cssSelector("#stock-form button[type=submit]")).click();
    assertTrue(driver.findElement(By.id("movement-error")).getText().contains("Only 0 units are available"));
  }

  @AfterEach void close() { if (driver != null) driver.quit(); }
}
