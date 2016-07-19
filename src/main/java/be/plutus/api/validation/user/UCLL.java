package be.plutus.api.validation.user;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( {ElementType.FIELD} )
@Retention( RetentionPolicy.RUNTIME )
@Constraint( validatedBy = {UCLLValidator.class} )
public @interface UCLL{

    String message() default "{be.plutus.api.validation.user.UCLL.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
