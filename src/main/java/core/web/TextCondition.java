package core.web;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import utils.logging.VegaLogger;

public class TextCondition {

  public static ExpectedCondition<Boolean> textIsNotEmpty(final core.web.CustomWebElement element) {
    return input -> {
      try {
        VegaLogger.info("Wait until text is not empty");
        String elementText = element.getText();
        return !elementText.isEmpty();
      } catch (StaleElementReferenceException e) {
        VegaLogger.error("Can't get text for element {}", element.toString());
        return null;
      }
    };
  }
}
