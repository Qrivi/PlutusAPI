package be.plutus.api.dto.request;

import be.plutus.api.validation.*;
import be.plutus.common.validation.WhitelistedPassword;
import be.plutus.core.model.currency.Currency;
import org.hibernate.validator.constraints.NotBlank;

public class AccountUpdateDTO{

    @Email( message = "{Email.AccountUpdateDTO.newEmail}", optional = true )
    @AvailableEmail( message = "{AvailableEmail.AccountUpdateDTO.email}" )
    private String newEmail;

    @Size( min = 8, message = "{Size.AccountUpdateDTO.newPassword}", optional = true )
    @WhitelistedPassword( message = "{WhitelistedPassword.AccountUpdateDTO.newPassword" )
    private String newPassword;

    @SupportedCurrency( message = "{SupportedCurrency.AccountUpdateDTO.newDefaultCurrency}", optional = true )
    private String newDefaultCurrency;

    @NotBlank( message = "{NotBlank.AccountUpdateDTO.password}" )
    @CorrectPassword( message = "{CorrectPassword.AccountUpdateDTO.password}" )
    private String password;

    public AccountUpdateDTO(){
    }

    public String getNewEmail(){
        return newEmail;
    }

    public String getNewPassword(){
        return newPassword;
    }

    public Currency getNewDefaultCurrency(){
        if( "".equals( newDefaultCurrency ) )
            return null;
        return Currency.valueOf( newDefaultCurrency.toUpperCase() );
    }

    public String getPassword(){
        return password;
    }
}
