package be.plutus.api.validation;

import be.plutus.core.model.location.Institution;
import be.plutus.core.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CorrectUsernameValidator implements ConstraintValidator<CorrectUsername, String>{

    @Autowired
    LocationService locationService;

    private String slur;

    @Override
    public void initialize( CorrectUsername username ){
        this.slur = username.institution();
    }

    @Override
    public boolean isValid( String value, ConstraintValidatorContext context ){
        if( value == null || value.equals( "" ) )
            return false;

        Institution institution = locationService.getInstitutionBySlur( slur );

        return value.matches( institution.getUsernamePattern() );
    }
}
