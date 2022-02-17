package utils.logging;

import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import utils.testrail.client.TestRailClient;

public class TestListener extends TestListenerAdapter {

  @Override
  public void onTestStart(ITestResult result) {
    ITestNGMethod method = result.getMethod();
    VegaLogger.info("Start test " + method.getMethodName() + " with TestRailId " + method.getDescription());
    TestRailClient.updateRun(Integer.parseInt(method.getDescription().substring(1)));
    super.onTestStart(result);
  }

  @Override
  public void onTestFailure(ITestResult result) {
    if (!result.isSuccess()) {
      ITestNGMethod method = result.getMethod();
      VegaLogger.takeScreenshot();
      VegaLogger.error("Test failed", result.getThrowable());
      TestRailClient.addResult("Test failed", Integer.parseInt(method.getDescription().substring(1)), 5);
    }
  }

  @Override
  public void onTestSuccess(ITestResult result) {
    if (result.isSuccess()) {
      ITestNGMethod method = result.getMethod();
      TestRailClient.addResult("Test completed successfuly",
          Integer.parseInt(method.getDescription().substring(1)), 1);
    }
  }

  @Override
  public void onTestSkipped(ITestResult result) {
    if (result.getStatus() == ITestResult.SKIP) {
      ITestNGMethod method = result.getMethod();
      TestRailClient.addResult("Test skipped",
          Integer.parseInt(method.getDescription().substring(1)), 3);
    }
  }
}

