package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import utils.logging.VegaLogger;
import utils.properties.SystemProperties;

public class DriverFactory {

  private static DriverNames driverName;
  private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

  public static void initDriver() throws Exception {
    driverName = DriverNames.valueOf(SystemProperties.DRIVER.toUpperCase());
    VegaLogger.info("Create driver " + driverName);
    switch (driverName) {
      case CHROME -> {
        WebDriverManager.chromedriver().driverVersion(SystemProperties.BROWSER_VERSION).setup();
        DRIVER.set(new ChromeDriver((ChromeOptions) (new DriverCapabilities(BrowserNames.CHROME)).getCapabilities()));
      }
      case FIREFOX -> {
        WebDriverManager.firefoxdriver().setup();
        DRIVER
            .set(new FirefoxDriver((FirefoxOptions) (new DriverCapabilities(BrowserNames.FIREFOX)).getCapabilities()));
      }
      default -> throw new Exception("No such driver in DriverFactory");
    }
    DRIVER.get();
  }

  public static WebDriver getDriver() {
    return DRIVER.get();
  }

}
