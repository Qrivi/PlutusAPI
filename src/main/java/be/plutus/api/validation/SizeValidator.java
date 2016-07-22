package be.plutus.api.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SizeValidator implements ConstraintValidator<Size, String>{

    private boolean optional;
    private int min, max;

    @Override
    public void initialize( Size size ){
        optional = size.optional();
        min = size.min();
        max = size.max();
    }

    @Override
    public boolean isValid( String value, ConstraintValidatorContext context ){
        if( optional && "".equals( value ) )
            return true;

        return value != null && value.length() >= min && value.length() <= max;
    }
}
