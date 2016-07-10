package be.plutus.api.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<Email, String>{

    private boolean optional;

    @Override
    public void initialize( Email email ){
        optional = email.optional();
    }

    @Override
    public boolean isValid( String value, ConstraintValidatorContext context ){
        if( optional && ( value == null || value.equals( "" ) ) )
            return true;

        if( value != null ){
            org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator validator =
                    new org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator();
            return validator.isValid( value, context );
        }
        return false;
    }
}
