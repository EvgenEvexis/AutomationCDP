package core.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.SkipException;
import utils.logging.VegaLogger;

public class WebElementsList extends core.web.CustomWebElement implements List<core.web.CustomWebElement> {

  public WebElementsList(WebDriver driver, String name) {
    super(driver, name);
  }

  public int size() {
    int size = getAll().size();
    VegaLogger.info("Size of list {} is " + size, name);
    return size;
  }

  @Override
  public boolean isEmpty() {
    return getAll().size() == 0;
  }

  @Override
  public boolean add(core.web.CustomWebElement webElement) {
    return true;
  }

  @Override
  public void add(int index, core.web.CustomWebElement element) {
  }

  @Override
  public boolean remove(Object o) {
    return false;
  }

  @Override
  public core.web.CustomWebElement remove(int index) {
    return null;
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return false;
  }

  @Override
  public boolean addAll(Collection<? extends core.web.CustomWebElement> c) {
    return false;
  }

  @Override
  public boolean addAll(int index, Collection<? extends core.web.CustomWebElement> c) {
    return false;
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    return false;
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    return false;
  }

  @Override
  public core.web.CustomWebElement set(int index, core.web.CustomWebElement element) {
    return null;
  }

  @Override
  public int indexOf(Object o) {
    return 0;
  }

  @Override
  public int lastIndexOf(Object o) {
    return 0;
  }

  @Override
  public WebElementsList template(String text) {
    if (copiedByLocator == null) {
      copiedByLocator = byLocator.toString();
    }
    refreshLocator(String.format(copiedByLocator, text));
    return this;
  }

  public List<String> getTexts() {
    return getTexts(true);
  }

  public List<String> getTexts(boolean setFocus) {
    List<core.web.CustomWebElement> allElements = getAll();
    allElements.forEach(core.web.CustomWebElement::waitToBeVisible);
    return allElements.stream().map(e -> e.getText(setFocus)).collect(Collectors.toList());
  }

  public boolean contains(Object o) {
    return getAll().contains(o);
  }

  public Iterator<core.web.CustomWebElement> iterator() {
    return getAll().iterator();
  }

  public Object[] toArray() {
    return getAll().toArray();
  }

  public <T> T[] toArray(T[] a) {
    return getAll().toArray(a);
  }

  public core.web.CustomWebElement get(int index) {
    try {
      return getAll().get(index);
    } catch (IndexOutOfBoundsException e) {
      VegaLogger.error("Can't find elements for locator {}", getLocator().toString());
    }
    return null;
  }

  public ListIterator<core.web.CustomWebElement> listIterator() {
    return getAll().listIterator();
  }

  public ListIterator<core.web.CustomWebElement> listIterator(int index) {
    return getAll().listIterator(index);
  }

  public List<core.web.CustomWebElement> subList(int fromIndex, int toIndex) {
    return getAll().subList(fromIndex, toIndex);
  }

  public boolean isDisplayed() {
    return getWebElements().get(0).isDisplayed();
  }

  public List<core.web.CustomWebElement> getAll() {
    return getWebElements();
  }

  private List<core.web.CustomWebElement> getWebElements() {
    List<core.web.CustomWebElement> elements = new ArrayList<>();
    try {
      wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(getLocator(), 0));
    } catch (Exception e) {
      VegaLogger.info("No webelements with locator {}", getLocator().toString());
    }
    getDriver().findElements(getLocator())
        .forEach(el -> elements.add(new core.web.CustomWebElement(driver, name, getLocator(), el)));
    return elements;
  }

  public core.web.CustomWebElement getElementContainingSubstring(String expectedText) {
    return findFirstElement(e -> e.getText(false).contains(expectedText) ? e : null, expectedText);
  }

  public core.web.CustomWebElement getChildWithText(String expectedText) {
    return findFirstElement(e -> e.getChild(By.xpath(String.format("//*[text()='%s']", expectedText))), expectedText);
  }

  core.web.CustomWebElement findFirstElement(UnaryOperator<core.web.CustomWebElement> elementExtractor, String expectedText) {
    for (core.web.CustomWebElement element : getWebElements()) {
      final core.web.CustomWebElement found = elementExtractor.apply(element);
      if (found != null && found.isDisplayed()) {
        return element;
      }
    }
    throw new SkipException("No element with '" + expectedText + "' text found");
  }

  public void clickAll() {
    getAll().forEach(core.web.CustomWebElement::click);
  }
}
