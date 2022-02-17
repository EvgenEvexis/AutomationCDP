package tests.ui;

import core.DriverFactory;
import java.lang.reflect.Method;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import tests.BaseTest;
import utils.logging.TestListener;
import utils.logging.VegaLogger;
import utils.testrail.client.TestRailClient;

@Listeners(TestListener.class)
public class BaseUITest extends BaseTest {

  @BeforeMethod(alwaysRun = true)
  protected void setUp(Method method) throws Exception {
    DriverFactory.initDriver();
    TestRailClient.createTestRailInstance();
  }

  @AfterMethod(alwaysRun = true)
  protected void tearDown(ITestResult result) {
    if (result.getStatus() == ITestResult.FAILURE) {
      TestRailClient.closeRun();
      VegaLogger.info("Close browser");
      driver().quit();
    }
    if (driver() != null) {
      TestRailClient.closeRun();
      driver().quit();
    }
  }

  protected WebDriver driver() {
    return DriverFactory.getDriver();
  }
}
