package tests.ui.youtube;


import java.lang.reflect.Method;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import steps.BaseSteps;
import steps.pagesteps.MainPageSteps;
import tests.ui.BaseUITest;
import utils.testrail.client.TestRailClient;

@Test(groups = {"sanity", "ui",})
public class SmokeUserFlow extends BaseUITest {

  @BeforeMethod
  @Override
  public void setUp(Method method) throws Exception {
    super.setUp(method);
    TestRailClient.setProjectSuiteId("EvexisAutomationCDP", "youtube suite");
    TestRailClient.createRun();
  }

  @Test(description = "T1")
  public void searchForAnything() {
    String searchText = MainPageSteps.getRandomString(6);
    BaseSteps.navigateToMainPage();
    MainPageSteps.enterSearchQuerry(searchText);
    MainPageSteps.validateStartMenuIsVisible();
    MainPageSteps.validateSearchInputIsVisible();
    MainPageSteps.searchInputTextIsCorrect(searchText);
    BaseSteps.refreshPage();
  }

}
