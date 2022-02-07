package steps.pagesteps;


import org.testng.Assert;
import pages.youtube.MainPage;
import steps.BaseSteps;

public class MainPageSteps extends BaseSteps {

  public static void enterSearchQuerry(String querry) {
    sc().mainPage.enterSearchQuery(querry);
    sc().mainPage.submitSearch();
  }

  public static void validateStartMenuIsVisible() {
    Assert.assertTrue(sc().mainPage.startMenuIsDisplayed(), "Start menu and icon is displayed");
  }

  public static void validateSearchInputIsVisible() {
    Assert.assertTrue(sc().mainPage.searchInputIsDisplayed(), "Search input is displayed");
  }

  public static void searchInputTextIsCorrect(String text) {
    Assert.assertEquals(sc().mainPage.getSearchQuery(), text, "Text in search querry is the same. Text is: " +text);
  }

}
