package core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import utils.logging.VegaLogger;
import utils.properties.EnvProperties;
import utils.properties.SystemProperties;

public class Environment {

  public static String getBaseUrl() {
    Pattern p = Pattern.compile("^\\w+\\.([\\w\\.-]+)(?::\\d+)?$");
    Matcher m = p.matcher(SystemProperties.BASE_URL);
    if (m.find()) {
      VegaLogger.info("Get base URL: " + m.group(1));
      return m.group(1);
    } else {
      VegaLogger.info("Get base URL: No match");
      return SystemProperties.BASE_URL;
    }
  }

  public static String getSiteUrl() {
    return getSiteUrl(SystemProperties.BASE_URL);
  }

  private static String getSiteUrl(String address) {
    final String localPort = EnvProperties.USE_LOCAL_PORT;

    if (isLocalhost()) {
      return "http://" + address + ":4200";
    }
    if (localPort.length() > 0) {
      return "http://" + address + ":" + localPort;
    }
    return "https://" + address;
  }

  public static String getEngagementSiteUrl() {
    return getSiteUrl(SystemProperties.BASE_URL_ST);
  }

  public static boolean isLocalhost() {
    return SystemProperties.BASE_URL.toLowerCase().contains("localhost");
  }
}
