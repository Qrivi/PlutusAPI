package be.plutus.api.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( {ElementType.FIELD} )
@Retention( RetentionPolicy.RUNTIME )
@Constraint( validatedBy = {CorrectUsernameValidator.class} )
public @interface CorrectUsername{

    String message() default "{be.plutus.api.validation.CorrectUsername.message}";

    String institution() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
