package pages.youtube;

import core.Environment;
import core.web.CustomWebElement;
import core.web.WebElementsList;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import pages.AbstractPage;
import utils.logging.VegaLogger;
import utils.properties.SystemProperties;

public class MainPage extends AbstractPage {


  @FindBy(css = "[id='start']")
  protected CustomWebElement startMenu;
  @FindBy(xpath = "//input[@id='search']")
  protected CustomWebElement searchInputField;
  @FindBy(css = "body")
  private CustomWebElement blankArea;

  public MainPage(WebDriver driver) {
    init(driver);
  }

  public void navigateTo() {
    driver.get(Environment.getSiteUrl());
  }

  public boolean startMenuIsDisplayed() {
    waitForVisibilityOfElement(startMenu);
    return startMenu.isDisplayed();
  }

  public boolean searchInputIsDisplayed() {
    waitForVisibilityOfElement(searchInputField);
    return searchInputField.isDisplayed();
  }

  public void enterSearchQuery(String text) {
    VegaLogger.info("Search querry is: " +text);
    waitAndClick(searchInputField);
    searchInputField.sendKeys(text);
  }

  public void submitSearch() {
    VegaLogger.info("Run search request");
    searchInputField.sendKeys(Keys.ENTER);
  }

  public String getSearchQuery() {
    return searchInputField.getText();
  }

  public void clickBlankArea() {
    blankArea.click();
  }


}
