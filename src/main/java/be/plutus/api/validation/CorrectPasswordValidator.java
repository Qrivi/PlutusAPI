package be.plutus.api.validation;

import be.plutus.api.security.context.SecurityContext;
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
    public void initialize( CorrectPassword password ){
    }

    @Override
    public boolean isValid( String value, ConstraintValidatorContext context ){
        Account account = accountService.getAccount( SecurityContext.getAccount().getId() );
        return account.isPasswordValid( value );
    }
}
