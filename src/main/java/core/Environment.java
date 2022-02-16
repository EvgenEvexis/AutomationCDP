package core;

import utils.properties.EnvProperties;
import utils.properties.SystemProperties;

public class Environment {

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

  public static boolean isLocalhost() {
    return SystemProperties.BASE_URL.toLowerCase().contains("localhost");
  }
}
