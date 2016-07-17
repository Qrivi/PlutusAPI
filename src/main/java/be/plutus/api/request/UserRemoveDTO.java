package be.plutus.api.request;

import be.plutus.api.validation.CorrectPassword;
import org.hibernate.validator.constraints.NotBlank;

public class UserRemoveDTO{

    @NotBlank( message = "{NotBlank.UserRemoveDTO.password}" )
    @CorrectPassword( message = "{CorrectPassword.UserRemoveDTO.password}" )
    private String password;

    public UserRemoveDTO(){
    }

    public String getPassword(){
        return password;
    }
}
