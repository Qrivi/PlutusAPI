package be.plutus.api.validation;

import be.plutus.core.model.currency.Currency;
import be.plutus.core.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SupportedInstitutionValidator implements ConstraintValidator<SupportedInstitution, String>{

    @Autowired
    LocationService locationService;

    private boolean optional;

    @Override
    public void initialize( SupportedInstitution institution ){
        optional = institution.optional();
    }

    @Override
    public boolean isValid( String value, ConstraintValidatorContext context ){
        if( optional && ( value == null || value.equals( "" ) ) )
            return true;

        return locationService.getInstitutionBySlur( value ) != null;
    }
}
