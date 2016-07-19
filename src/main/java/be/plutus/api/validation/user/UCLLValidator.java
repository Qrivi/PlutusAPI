package be.plutus.api.validation.user;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UCLLValidator implements ConstraintValidator<UCLL, String>{

    @Override
    public void initialize( UCLL username ){
    }

    @Override
    public boolean isValid( String value, ConstraintValidatorContext context ){
        if( value == null || value.equals( "" ) )
            return false;
        return value.toLowerCase().matches( "^([m|r|s][0-9]{7})$" );
    }
}
