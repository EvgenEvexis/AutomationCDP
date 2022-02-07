package utils.readers;

import com.google.common.reflect.ClassPath;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import utils.logging.VegaLogger;

public class ClassLoader {

  public List<Class<?>> loadClassesInPackage(String packageName) {
    List<Class<?>> classes = new ArrayList();
    try {
      for (ClassPath.ClassInfo classInfo
          : ClassPath.from(getClass().getClassLoader()).getTopLevelClasses(packageName)) {
        Class<?> cls;

        cls = classInfo.load();

        if (!cls.isInterface()) {
          classes.add(cls);
        }
      }
    } catch (IOException e) {
      VegaLogger.error("Couldn't load class ", e);
    }
    return classes;
  }
}
