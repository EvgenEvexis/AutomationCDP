package steps;

import core.DriverFactory;
import core.web.CustomWebElement;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pages.patron.MainPage;
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

  public static void navigateToMainPageForEngagement() {
    VegaLogger.info("Navigate to main page for engagement customer");
    sc().mainPage = new MainPage(driver());
    sc().mainPage.navigateToEngagement();
  }

  public static void refreshPage() {
    VegaLogger.info("Refresh page {}", getUrl());
    driver().navigate().refresh();
  }

  protected static void clickOnText(String elementsText) {
    new CustomWebElement(driver(), elementsText, String.format("//*[text()='%s']", elementsText))
        .click();
  }

  protected static void validateTextIsPresented(String elementsText) {
    Assert.assertTrue(
        new CustomWebElement(driver(), elementsText,
            String.format("//*[contains(text(),'%s')]", elementsText))
            .isDisplayed(),
        String.format("Element with text %s is not visible", elementsText));
  }

  public static void clickAcceptCookies() {
    sc().mainPage.acceptCookies();
  }

  public static void clickBlankArea() {
    sc().mainPage = new MainPage(driver());
    sc().mainPage.clickBlankArea();
  }

  public static void enableFeatureFlag(String key, boolean value) {
    VegaLogger.info("Enable feature flag {}", key);
    JavascriptExecutor js = (JavascriptExecutor) (driver());
    js.executeScript("localStorage.setItem(arguments[0],arguments[1])", key, value);
  }

  public static String getRandomString(int length) {
    return RandomStringUtils.randomAlphabetic(length).toLowerCase();
  }

  public static String randomName(String prefix) {
    return randomNames(prefix)[0].toLowerCase();
  }

  public static String[] randomNames(String prefix) {
    final int randomCharsCount = prefix.length() <= 5 ? 8 - prefix.length() : 3;
    final String firstName = prefix + RandomStringUtils.randomAlphabetic(randomCharsCount);
    final String lastName = prefix + RandomStringUtils.randomAlphabetic(randomCharsCount);
    return new String[]{firstName, lastName};
  }

  protected static void hardWait(int millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      //Ignore it
    }
  }
}
