package pages;

import core.CustomPageFactory;
import core.web.CustomWebElement;
import core.web.WebElementsList;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.logging.VegaLogger;
import utils.properties.SystemProperties;

public class AbstractPage<T extends AbstractPage> {

  @FindBy(xpath = "//a[contains(.,'%s')]")
  private CustomWebElement linkWithText;

  public static final String SITE_URL_ENG = String.format("https://%s", SystemProperties.BASE_URL_ST);
  protected static final ThreadLocal<WebDriverWait> WAIT = new ThreadLocal<>();

  public WebDriver driver;

  public T init(WebDriver driver) {
    this.driver = driver;
    WAIT.set(new WebDriverWait(driver, 10));
    driver.manage().window().setSize(new Dimension(1920, 1080));
    CustomPageFactory.initElements(driver, this);
    return (T) this;
  }

  public void waitUntilElementsContainText(WebElementsList elements) {
    VegaLogger.info("Wait until all elements {} will contain any text", elements.toString());
    getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(elements.getLocator()));
    elements.getAll().forEach(el -> getWait().until(new TextCondition(el)));
  }

  protected FluentWait<WebDriver> getWait() {
    return WAIT.get();
  }

  protected void hardWait(int millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      //Ignore it
    }
  }

  protected void waitForList(By by) {
    try {
      getWait().withTimeout(Duration.ofSeconds(20))
          .until(ExpectedConditions.numberOfElementsToBeMoreThan(by, 0));
    } catch (TimeoutException e) {
      VegaLogger.error(e);
      Assert.fail("The list loading timed out.");
    }
  }

  protected void waitForInvisibilityOfElement(CustomWebElement webElement) {
    waitForInvisibilityOfElement(webElement, 5);
  }

  protected void waitForInvisibilityOfElement(CustomWebElement webElement, long seconds) {
    VegaLogger.info("Wait until {} becomes invisible", webElement.toString());
    getWait().withTimeout(Duration.ofSeconds(seconds)).until(ExpectedConditions.invisibilityOf(webElement));
  }

  protected void waitUntilLinkWithTextIsPresented(String expectedText) {
    getWait().until(ExpectedConditions.visibilityOf(linkWithText.template(expectedText)));
  }

  protected void waitForVisibilityOfElement(CustomWebElement webElement) {
    waitForVisibilityOfElement(webElement, 5);
  }

  protected void waitForVisibilityOfElement(CustomWebElement webElement, long seconds) {
    VegaLogger.info("Wait until {} becomes visible", webElement.toString());
    getWait().withTimeout(Duration.ofSeconds(seconds)).until(ExpectedConditions.visibilityOf(webElement));
  }

  protected void waitForElementToBeClickable(CustomWebElement webElement) {
    waitForElementToBeClickable(webElement, 5);
  }

  protected void waitForElementToBeClickable(CustomWebElement webElement, long seconds) {
    VegaLogger.info("Wait until {} becomes visible", webElement.toString());
    getWait().withTimeout(Duration.ofSeconds(seconds)).until(ExpectedConditions.elementToBeClickable(webElement));
  }

  protected void waitAndClick(CustomWebElement button, long seconds) {
    waitForElementToBeClickable(button, seconds);
    button.click();
  }

  protected void waitAndClick(CustomWebElement button) {
    waitAndClick(button, 5);
  }

  public void switchToNewWindow() {
    getWait().until(ExpectedConditions.numberOfWindowsToBe(2));
    for (String winHandle : driver.getWindowHandles()) {
      driver.switchTo().window(winHandle);
    }
  }

  private static class TextCondition implements ExpectedCondition {

    WebElement element;

    public TextCondition(CustomWebElement element) {
      this.element = element;
    }

    @Override
    public Boolean apply(Object input) {
      try {
        return element.getText().length() > 0;
      } catch (WebDriverException e) {
        return false;
      }
    }
  }
}
