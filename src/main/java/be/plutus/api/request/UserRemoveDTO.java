package be.plutus.api.request;

import be.plutus.api.validation.CorrectPassword;
import org.hibernate.validator.constraints.NotBlank;

public class UserRemoveDTO{

    @NotBlank( message = "{NotBlank.AccountRemoveDTO.password}" )
    @CorrectPassword( message = "{CorrectPassword.AccountRemoveDTO.password}" )
    private String password;

    public UserRemoveDTO(){
    }

    public String getPassword(){
        return password;
    }
}
