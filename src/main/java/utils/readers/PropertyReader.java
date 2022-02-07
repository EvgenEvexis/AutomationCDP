package utils.readers;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Properties;
import org.apache.commons.lang3.reflect.FieldUtils;
import utils.logging.VegaLogger;
import utils.properties.FilePath;
import utils.properties.Property;

public class PropertyReader {

  private static final Properties PROPERTIES = new Properties();

  public static void readProperties() {
    PROPERTIES.putAll(System.getProperties());

    List<Class<?>> classesList = new ClassLoader().loadClassesInPackage("utils.properties");
    String filePath;
    for (Class<?> clazz : classesList) {
      Field[] fields = FieldUtils.getFieldsWithAnnotation(clazz, Property.class);
      if (clazz.isAnnotationPresent(FilePath.class)) {
        filePath = clazz.getAnnotation(FilePath.class).value();
        try {
          PROPERTIES.load(new FileInputStream(filePath));
        } catch (IOException e) {
          VegaLogger.error("Couldn't read file " + filePath, e);
        }
      }
      for (Field field : fields) {
        String key = field.getAnnotation(Property.class).value();
        try {
          String value = PROPERTIES.getProperty(key);
          field.set(String.class, value);
          VegaLogger.info("Set property " + clazz.getName() + "::" + field.getName() + " with value " + value);
        } catch (IllegalAccessException e) {
          VegaLogger.error("Couldn't set field " + field.getName() + " for property class " + clazz.getName(), e);
        }
      }
    }
  }
}
