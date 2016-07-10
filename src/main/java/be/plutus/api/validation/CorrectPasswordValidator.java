package be.plutus.api.validation;

import be.plutus.core.model.account.Account;
import be.plutus.core.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CorrectPasswordValidator implements ConstraintValidator<CorrectPassword, String>{

    @Autowired
    AccountService accountService;

    @Override
    public void initialize( CorrectPassword email ){
    }

    @Override
    public boolean isValid( String value, ConstraintValidatorContext context ){

        Account account = accountService.getAccount( (Integer)SecurityContextHolder.getContext().getAuthentication().getPrincipal() );

        return account.isPasswordValid( value );
    }
}
