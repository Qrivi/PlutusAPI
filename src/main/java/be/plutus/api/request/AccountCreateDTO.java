package be.plutus.api.request;

import be.plutus.api.validation.AvailableEmail;
import be.plutus.api.validation.SupportedCurrency;
import be.plutus.common.validation.WhitelistedPassword;
import be.plutus.core.model.currency.Currency;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

public class AccountCreateDTO{

    @NotBlank( message = "{NotBlank.AccountCreateDTO.email}" )
    @Email( message = "{Email.AccountCreateDTO.email}" )
    @AvailableEmail( message = "{AvailableEmail.AccountCreateDTO.email}" )
    private String email;

    @NotBlank( message = "{NotBlank.AccountCreateDTO.password}" )
    @Size( min = 8, message = "{Size.AccountCreateDTO.password}" )
    @WhitelistedPassword( message = "{WhitelistedPassword.AccountCreateDTO.password}" )
    private String password;

    @SupportedCurrency( message = "{SupportedCurrency.AccountCreateDTO.defaultCurrency}", optional = true )
    private String defaultCurrency;

    public AccountCreateDTO(){
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

    public Currency getDefaultCurrency(){
        if( defaultCurrency == null || defaultCurrency.equals( "" ) )
            return null;
        return Currency.valueOf( defaultCurrency.toUpperCase() );
    }
}
