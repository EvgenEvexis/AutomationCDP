package utils.logging;

import core.DriverFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Reporter;

public class VegaLogger {

  private static final Logger LOG = LogManager.getLogger(VegaLogger.class);

  public static void info(String message) {
    LOG.info(message);
    Reporter.log(timeStamp() + "INFO: " + message + "</br>");
  }

  public static void info(String message, String replacement) {
    info(message.replace("{}", replacement));
  }

  public static void info(String message, String... replacement) {
    Arrays.stream(replacement).forEach(r -> message.replace("{}", r));
  }

  public static void info(String message, int replacement) {
    info(message, String.valueOf(replacement));
  }

  public static void info(String message, boolean checked) {
    info(message, String.valueOf(checked));
  }

  public static void info(String message, Throwable throwable) {
    LOG.info(message, throwable);
    Reporter.log(timeStamp() + "INFO: " + message + "</br>" + throwable.getMessage());
  }

  public static void debug(String message) {
    LOG.debug(message);
    Reporter.log(timeStamp() + "DEBUG: " + message + "</br>");
  }

  public static void debug(String message, String replacement) {
    debug(message.replace("{}", replacement));
  }

  public static void debug(String message, String... replacement) {
    Arrays.stream(replacement).sequential().forEach(r -> message.replace("{}", r));
  }

  public static void error(String message, String replacement) {
    error(message.replace("{}", replacement));
  }

  public static void error(String message) {
    LOG.error(message);
    Reporter.log(timeStamp() + "ERROR: " + message + "</br>");
  }

  public static void error(String message, Throwable throwable) {
    LOG.error(message, throwable);
    Reporter.log(timeStamp() + "ERROR: " + message + "</br>" + throwable.getMessage());
  }

  public static void error(String message, String replacement, Throwable throwable) {
    error(message.replace("{}", replacement), throwable);
  }

  public static void error(Throwable throwable) {
    LOG.error(throwable);
    Reporter.log(timeStamp() + throwable.getMessage());
  }

  private static String timeStamp() {
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + ": ";
  }

  public static void takeScreenshot() {
    File scrFile = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.FILE);
    Reporter.log("<br><a href='data:image/png;base64," + encodeFileToBase64Binary(scrFile)
        + "'> CLICK TO SEE SCREENSHOT </a></br>");
  }

  public static void takeScreenshot(String message) {
    VegaLogger.info(message);
    takeScreenshot();
  }

  private static String encodeFileToBase64Binary(File file) {
    String encodedfile = null;
    try {
      FileInputStream fileInputStreamReader = new FileInputStream(file);
      byte[] bytes = new byte[(int) file.length()];
      fileInputStreamReader.read(bytes);
      encodedfile = new String(Base64.encodeBase64(bytes), StandardCharsets.UTF_8);
    } catch (FileNotFoundException e) {
      VegaLogger.error("Incorrect file {} path", file.getAbsolutePath(), e);
    } catch (IOException e) {
      VegaLogger.error("Error during file {} reading", file.getAbsolutePath(), e);
    }
    return encodedfile;
  }
}
