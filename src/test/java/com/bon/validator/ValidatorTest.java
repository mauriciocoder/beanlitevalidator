package com.bon.validator;

import com.bon.validator.annotation.Required;
import static com.bon.validator.annotation.InvalidValue.BLANK;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ValidatorTest {
  @Test
  public void testIsValidStringBean() {
    // null String test
    StringBean b = new StringBean();
    assertEquals(false, Validator.isValid(b));
  
    // empty String test
    b.setField("");
    assertEquals(true, Validator.isValid(b));
  
    // filled String test
    b.setField("Mentor");
    assertEquals(true, Validator.isValid(b));
  }
  
  @Test
  public void testIsValidStringFilledBean() {
    // null String test
    StringFilledBean b = new StringFilledBean();
    assertEquals(false, Validator.isValid(b));
    
    // empty String test
    b.setField("");
    assertEquals(false, Validator.isValid(b));
    
    // filled String test
    b.setField("Mentor");
    assertEquals(true, Validator.isValid(b));
  }
}

class StringBean {
  @Required
  private String field;
  
  public void setField(String field) {
    this.field = field;
  }
}

class StringFilledBean {
  @Required(invalidValues={BLANK})
  private String field;
  
  public void setField(String field) {
    this.field = field;
  }
}
