package utils.properties;

@FilePath(value = "./src/test/resources/env.properties")
public class EnvProperties {

  @Property(value = "env.site_url")
  public static String SITE_URL_KEY;

  @Property(value = "env.use_local_port")
  public static String USE_LOCAL_PORT;
}
