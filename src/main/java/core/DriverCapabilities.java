package core;

import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.safari.SafariOptions;
import utils.logging.VegaLogger;

public class DriverCapabilities {

  private MutableCapabilities capabilities;

  public DriverCapabilities(BrowserNames browser) {
    switch (browser) {
      case CHROME -> {
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("intl.accept_languages", "eu-EU");
        options.setExperimentalOption("prefs", prefs);
        capabilities = options;
      }
      case FIREFOX -> capabilities = new FirefoxOptions();
      case EDGE -> capabilities = new EdgeOptions();
      case SAFARI-> capabilities = new SafariOptions();
      case IE11-> capabilities = new InternetExplorerOptions();
      default -> {}
    }
  }

  public MutableCapabilities getCapabilities() {
    VegaLogger.info("Browser options are : {}", capabilities.toString());
    return capabilities;
  }
}
