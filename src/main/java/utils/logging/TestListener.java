package utils.logging;

import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class TestListener extends TestListenerAdapter {

  @Override
  public void onTestStart(ITestResult result) {
    ITestNGMethod method = result.getMethod();
    VegaLogger.info("Start test " + method.getMethodName() + " with TestRailId " + method.getDescription());
    super.onTestStart(result);
  }

  @Override
  public void onTestFailure(ITestResult result) {
    if (!result.isSuccess()) {
      VegaLogger.takeScreenshot();
      VegaLogger.error("Test failed", result.getThrowable());
    }
  }
}

