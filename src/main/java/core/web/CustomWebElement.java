package core.web;

import static org.apache.commons.lang3.StringUtils.isBlank;

import core.tools.CacheValue;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByClassName;
import org.openqa.selenium.By.ByCssSelector;
import org.openqa.selenium.By.ById;
import org.openqa.selenium.By.ByLinkText;
import org.openqa.selenium.By.ByName;
import org.openqa.selenium.By.ByPartialLinkText;
import org.openqa.selenium.By.ByTagName;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.logging.VegaLogger;

public class CustomWebElement implements WebElement {

  private static final int WAIT_TIMEOUT_SEC = 5;
  private static final int SLEEP_TIMEOUT_MS = 100;
  protected final WebDriver driver;
  protected final String name;
  protected final WebDriverWait wait;
  protected By byLocator;
  protected String copiedByLocator;
  private final CacheValue<WebElement> webElement = new CacheValue<>();

  public CustomWebElement(WebDriver driver, String name) {
    this.driver = driver;
    wait = new WebDriverWait(this.driver, WAIT_TIMEOUT_SEC, SLEEP_TIMEOUT_MS);
    this.name = name;
  }

  public CustomWebElement(WebDriver driver, String name, String locator) {
    this.driver = driver;
    wait = new WebDriverWait(this.driver, WAIT_TIMEOUT_SEC, SLEEP_TIMEOUT_MS);
    this.name = name;
    this.byLocator = By.xpath(locator);
  }

  public CustomWebElement(WebDriver driver, String name, By locator, WebElement el) {
    this.driver = driver;
    wait = new WebDriverWait(this.driver, WAIT_TIMEOUT_SEC, SLEEP_TIMEOUT_MS);
    this.name = name;
    this.byLocator = locator;
    this.setWebElement(el);
  }

  public void setLocator(By locator) {
    byLocator = locator;
  }

  public By getLocator() {
    return byLocator;
  }

  public void setWebElement(WebElement el) {
    webElement.setForce(el);
  }

  public WebDriver getDriver() {
    return driver;
  }

  public WebElement getWebElement() {
    if (webElement.hasValue()) {
      return webElement.get();
    } else {
      try {
        return wait.until(ExpectedConditions.presenceOfElementLocated(byLocator));
      } catch (TimeoutException e) {
        throw new TimeoutException("Don't see " + this, e);
      }
    }
  }

  public void click() {
    click(5);
  }

  public void click(int timeout) {
    click(new WebDriverWait(this.driver, timeout, SLEEP_TIMEOUT_MS));
  }

  private void click(WebDriverWait waitForClick) {
    VegaLogger.debug("Click on {}", toString());
    WebElement element = getWebElement();
    try {
      waitForClick.until(ExpectedConditions.elementToBeClickable(element)).click();
    } catch (Exception e) {
      try {
        VegaLogger.info("Can't click element with regular click", e);
        VegaLogger.takeScreenshot();
        waitForClick.until(new HiddenCondition(element));
      } catch (TimeoutException ex) {
        if (driver.findElements(byLocator).size() == 0) {
          throw new NoSuchElementException("No such element");
        }
        throw new TimeoutException("Failed to click with JS on " + this);
      }
    }
  }

  @Override
  public void submit() {
    wait.until(ExpectedConditions.visibilityOf(this)).submit();
  }

  public void sendKeys(CharSequence... value) {
    VegaLogger.debug("Send keys " + Arrays.toString(value) + " to " + name);
    try {
      WebElement element = getWebElement();
      wait.until(ExpectedConditions.elementToBeClickable(element));
      element.sendKeys(value);
    } catch (TimeoutException e) {
      throw new TimeoutException("Failed to send keys to " + name + " with locator " + byLocator);
    }
  }

  public void setText(CharSequence... value) {
    sendKeys(value);
  }

  public void clear() {
    VegaLogger.debug("Clear field {}", name);
    try {
      WebElement element = getWebElement();
      ((JavascriptExecutor) driver)
          .executeScript("arguments[0].value = '';", element);
      wait.until(ExpectedConditions.elementToBeClickable(element)).clear();
    } catch (TimeoutException e) {
      throw new TimeoutException("Failed to clean " + name + " with locator " + byLocator);
    }
  }

  public String getTagName() {
    VegaLogger.debug("Get tag name for {}", name);
    return getWebElement().getTagName();
  }

  public boolean isSelected() {
    boolean result = getWebElement().isSelected();
    VegaLogger.debug("Element " + name + " is selected = " + result);
    return result;
  }

  public boolean isEnabled() {
    boolean result = getWebElement().isEnabled();
    VegaLogger.debug("Element " + name + " is enabled = " + result);
    return result;
  }

  public String getText() {
    return getText(true);
  }

  public String getText(boolean setFocus) {
    if (setFocus) {
      setFocus();
    }
    WebElement el = getWebElement();
    String text = el.getText();
    if (!isBlank(text)) {
      VegaLogger.debug("Get inner text -->" + text + "<-- from " + name);
      return text;
    }

    String value = el.getAttribute("value");
    if (!isBlank(value)) {
      VegaLogger.debug("Get value text -->" + value + "<-- from " + name);
      return value;
    } else {
      el = getWebElement();
      text = el.getText();
      VegaLogger.debug("Get inner text -->" + text + "<-- from " + name);
      return text;
    }
  }

  public List<WebElement> findElements(By by) {
    return getWebElement().findElements(by);
  }

  public CustomWebElement findElement(By by) {
    return new CustomWebElement(driver, String.format("Child of %s", name), by, getWebElement().findElement(by));
  }

  public CustomWebElement getChild(CustomWebElement webElement) {
    return findElement(webElement.getLocator());
  }

  public CustomWebElement getChild(By by) {
    return findElement(by);
  }

  public List<WebElement> getChildren(CustomWebElement webElement) {
    return findElements(webElement.getLocator());
  }

  public boolean isDisplayed() {
    try {
      WebElement el = getWebElement();
      return el.isDisplayed();
    } catch (Exception ex) {
      return false;
    }
  }

  public Point getLocation() {
    VegaLogger.debug("Get location for " + name);
    return getWebElement().getLocation();
  }

  public Dimension getSize() {
    VegaLogger.debug("Get size for " + name);
    return getWebElement().getSize();
  }

  public Rectangle getRect() {
    VegaLogger.debug("Get Rect for " + name);
    return getWebElement().getRect();
  }

  public String getCssValue(String s) {
    VegaLogger.debug("Get CSS value " + s + " for " + name);
    return getWebElement().getCssValue(s);
  }

  public <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException {
    VegaLogger.debug("Get screenshot for " + name);
    return getWebElement().getScreenshotAs(outputType);
  }

  public String getAttribute(String attribute) {
    VegaLogger.debug("Get attribute " + attribute + "  for " + name);
    return getWebElement().getAttribute(attribute);
  }

  private void executeScript(String s, WebElement element) {
    ((JavascriptExecutor) driver).executeScript(s, element);
  }

  public void refresh() {
    if (copiedByLocator == null) {
      copiedByLocator = byLocator.toString();
    }
    refreshLocator(copiedByLocator);
  }

  public CustomWebElement template(String text) {
    if (copiedByLocator == null) {
      copiedByLocator = byLocator.toString();
    }
    refreshLocator(String.format(copiedByLocator, text));
    webElement.setForce(getWebElement());
    return this;
  }

  void refreshLocator(String locator) {
    locator = locator.replaceAll("(By\\.)(\\w+)(: )", "").trim();
    if (ById.class.equals(byLocator.getClass())) {
      byLocator = new ById(locator);
    } else if (ByLinkText.class.equals(byLocator.getClass())) {
      byLocator = new ByLinkText(locator);
    } else if (ByPartialLinkText.class.equals(byLocator.getClass())) {
      byLocator = new ByPartialLinkText(locator);
    } else if (ByName.class.equals(byLocator.getClass())) {
      byLocator = new ByName(locator);
    } else if (ByTagName.class.equals(byLocator.getClass())) {
      byLocator = new ByTagName(locator);
    } else if (ByXPath.class.equals(byLocator.getClass())) {
      byLocator = new ByXPath(locator);
    } else if (ByClassName.class.equals(byLocator.getClass())) {
      byLocator = new ByClassName(locator);
    } else if (ByCssSelector.class.equals(byLocator.getClass())) {
      byLocator = new ByCssSelector(locator);
    }
  }

  @Override
  public String toString() {
    return String.format("Webelement %s with locator %s", name, byLocator);
  }

  public void setWaiter(Long waiter) {
    wait.withTimeout(Duration.ofSeconds(waiter));
  }

  public String getHref() {
    return getAttribute("href");
  }

  public void setFocus() {
    VegaLogger.info("Set focus on element {}", toString());
    executeScript("arguments[0].scrollIntoView(true);", this.getWebElement());
    executeScript("arguments[0].focus();", this.getWebElement());
  }

  public boolean hasChild(CustomWebElement child) {
    boolean hasChild = findElements(child.getLocator()).size() > 0;
    VegaLogger
        .info(String.format("Element %s has %s as child = %s", name, child.getLocator(), hasChild));
    return hasChild;
  }

  protected void waitToBeVisible() {
    wait.until(ExpectedConditions.visibilityOf(this));
  }

  public boolean textIs(String expectedText) {
    VegaLogger.debug("Check that text {} is presented in {}", expectedText, toString());
    wait.until(core.web.TextCondition.textIsNotEmpty(this));
    return getText().equals(expectedText);
  }

  public void hover() {
    Actions actions = new Actions(driver);
    actions.moveToElement(getWebElement()).perform();
  }

  public class HiddenCondition implements ExpectedCondition {

    WebElement element;

    public HiddenCondition(WebElement element) {
      VegaLogger.info("Try to click element with JS");
      this.element = element;
    }

    @Override
    public Boolean apply(Object input) {
      try {
        executeScript("arguments[0].scrollIntoView(true);", element);
        executeScript("arguments[0].focus();", element);
        try {
          element.click();
        } catch (WebDriverException ex) {
          element.sendKeys(Keys.RETURN);
        }
        return true;
      } catch (WebDriverException e) {
        return false;
      }
    }
  }
}
