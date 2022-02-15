package tests;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import steps.BaseSteps;
import utils.logging.TestListener;
import utils.properties.SystemProperties;
import utils.readers.PropertyReader;

@Listeners(TestListener.class)
public class BaseTest {

  protected String apiInstance;

  @BeforeClass(alwaysRun = true)
  protected void readProperties() {
    PropertyReader.readProperties();
    apiInstance = String.format("https://%s/", SystemProperties.BASE_URL);
    setRestAssuredToIgnoreUnknownFields();
  }

  @BeforeMethod(alwaysRun = true)
  public void setup() {
    BaseSteps.init();
  }

  private void setRestAssuredToIgnoreUnknownFields() {
    RestAssured.config = RestAssuredConfig.config()
        .objectMapperConfig(new ObjectMapperConfig().jackson2ObjectMapperFactory(
            (cls, charset) -> {
              ObjectMapper om = new ObjectMapper().findAndRegisterModules();
              om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
              return om;
            }
        ));
  }
}
