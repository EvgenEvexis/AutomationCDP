package utils.properties;

@FilePath(value = "./src/test/resources/sys.properties")
public class SystemProperties {

  @Property(value = "base_url")
  public static String BASE_URL;

  @Property(value = "base_url_st")
  public static String BASE_URL_ST;

  @Property(value = "browser")
  public static String BROWSER;

  @Property(value = "browser_version")
  public static String BROWSER_VERSION;

  @Property(value = "driver")
  public static String DRIVER;

  @Property(value = "platform")
  public static String PLATFORM;

  @Property(value = "screen_resolution")
  public static String SCREEN_RESOLUTION;
}
