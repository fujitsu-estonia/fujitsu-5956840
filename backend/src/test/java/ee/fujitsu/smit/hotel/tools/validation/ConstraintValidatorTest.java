package ee.fujitsu.smit.hotel.tools.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.atomic.AtomicReference;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public abstract class ConstraintValidatorTest {

  @Mock protected ConstraintValidatorContext validationContext;
  @Mock protected ConstraintValidatorContext.ConstraintViolationBuilder constraintViolationBuilder;

  protected AtomicReference<String> messageTemplateHolder;

  @BeforeEach
  void setUpValidationContext() {
    messageTemplateHolder = new AtomicReference<>();
    lenient().doAnswer(inv -> {
      messageTemplateHolder.set(inv.getArgument(0, String.class));
      return constraintViolationBuilder;
    }).when(validationContext).buildConstraintViolationWithTemplate(anyString());
    lenient().doReturn(validationContext).when(constraintViolationBuilder).addConstraintViolation();
  }
}
