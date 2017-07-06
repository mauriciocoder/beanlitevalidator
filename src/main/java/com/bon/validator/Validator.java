package com.bon.validator;

import com.bon.validator.annotation.InvalidValue;
import static com.bon.validator.annotation.InvalidValue.BLANK;
import com.bon.validator.annotation.Required;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Validator {
  /**
   * Validate the object checking which fields are required by annotation
   * @param obj
   * @param <T>
   * @return
   */
  public static <T> boolean isValid(T obj) {
    return validate(obj).count() <= 0;
  }
  
  public static <T> Stream<Violation> validate(T obj) {
    Field[] fields = obj.getClass().getDeclaredFields();
    List<Violation> violations = new ArrayList<Violation>();
    for (Field field : fields) {
      Required r = field.getAnnotation(Required.class);
      if (r != null) {
        field.setAccessible(true);
        try {
          Class<?> fieldType = field.getType();
          Object fieldValue = field.get(obj);
          if (fieldValue == null) {
            violations.add(new Violation(field.toString(), "is null"));
            continue;
          }
          // evaluate string object
          if (fieldType == String.class) {
            InvalidValue[] invalidValues = r.invalidValues();
            for (InvalidValue value : invalidValues) {
              if (value == BLANK) {
                if (StringUtils.isBlank((String) fieldValue)) {
                  // Blank string
                  violations.add(new Violation(field.toString(), "is blank"));
                }
              }
            }
          }
        } catch (IllegalAccessException e) {
          throw new RuntimeException(e);
        }
      }
    }
    return violations.stream();
  }
}
