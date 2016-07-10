package be.plutus.api.validation;

import be.plutus.core.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AvailableEmailValidator implements ConstraintValidator<AvailableEmail, String>{

    @Autowired
    AccountService accountService;

    @Override
    public void initialize( AvailableEmail email ){
    }

    @Override
    public boolean isValid( String value, ConstraintValidatorContext context ){
        return accountService.getAccount( value ) == null;
    }
}
