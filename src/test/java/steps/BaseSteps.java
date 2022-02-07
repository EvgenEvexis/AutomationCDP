package steps;

import core.DriverFactory;
import core.web.CustomWebElement;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pages.youtube.MainPage;
import utils.logging.VegaLogger;

public class BaseSteps {

  protected static final ThreadLocal<SharedContext> SC = new ThreadLocal<>();

  protected static WebDriver driver() {
    return DriverFactory.getDriver();
  }

  public static SharedContext sc() {
    return SC.get();
  }

  public static void init() {
    SC.set(new SharedContext());
  }

  public static String getUrl() {
    return driver().getCurrentUrl();
  }

  public static void navigateToMainPage() {
    VegaLogger.info("Navigate to main page");
    sc().mainPage = new MainPage(driver());
    sc().mainPage.navigateTo();
  }

   public static void refreshPage() {
    VegaLogger.info("Refresh page {}", getUrl());
    driver().navigate().refresh();
  }

  protected static void validateTextIsPresented(String elementsText) {
    Assert.assertTrue(
        new CustomWebElement(driver(), elementsText,
            String.format("//*[contains(text(),'%s')]", elementsText))
            .isDisplayed(),
        String.format("Element with text %s is not visible", elementsText));
  }

    public static void clickBlankArea() {
    sc().mainPage = new MainPage(driver());
    sc().mainPage.clickBlankArea();
  }

  public static String getRandomString(int length) {
    return RandomStringUtils.randomAlphabetic(length).toLowerCase();
  }

  protected static void hardWait(int millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      //Ignore it
    }
  }
}
