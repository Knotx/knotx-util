package io.knotx.commons.error;

import static org.junit.jupiter.api.Assertions.*;

import io.reactivex.exceptions.CompositeException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ExceptionUtilsTest {

  @Test
  @DisplayName("Expect single exception when not composite")
  void singleException() {
    Throwable single = new RuntimeException();

    List<Throwable> flattened = ExceptionUtils.flatIfComposite(single);

    assertEquals(Collections.singletonList(single), flattened);
  }

  @Test
  @DisplayName("Expect flattened composite exception")
  void compositeException() {
    Throwable first = new RuntimeException();
    Throwable second = new IllegalStateException();
    Throwable composite = new CompositeException(first, second);

    List<Throwable> flattened = ExceptionUtils.flatIfComposite(composite);

    assertEquals(Arrays.asList(first, second), flattened);
  }

}
