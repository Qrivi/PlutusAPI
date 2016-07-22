package be.plutus.api.validation;

import be.plutus.core.model.currency.Currency;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SupportedCurrencyValidator implements ConstraintValidator<SupportedCurrency, String>{

    private boolean optional;

    @Override
    public void initialize( SupportedCurrency currency ){
        optional = currency.optional();
    }

    @Override
    public boolean isValid( String value, ConstraintValidatorContext context ){
        if( optional && "".equals( value ) )
            return true;

        if( value != null )
            for( Currency c : Currency.values() )
                if( c.name().equals( value.toUpperCase() ) )
                    return true;
        return false;
    }
}
