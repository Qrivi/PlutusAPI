package be.plutus.api.dto.request;

import be.plutus.api.validation.CorrectPassword;
import org.hibernate.validator.constraints.NotBlank;

public class AccountRemoveDTO{

    @NotBlank( message = "{NotBlank.AccountRemoveDTO.password}" )
    @CorrectPassword( message = "{CorrectPassword.AccountRemoveDTO.password}" )
    private String password;

    public AccountRemoveDTO(){
    }

    public String getPassword(){
        return password;
    }
}
