package be.plutus.api.dto.request;

import be.plutus.api.validation.CorrectPassword;
import org.hibernate.validator.constraints.NotBlank;

public class UserAuthenticationDTO{

    @NotBlank( message = "{NotBlank.UserAuthenticationDTO.password}" )
    @CorrectPassword( message = "{CorrectPassword.UserAuthenticationDTO.password}" )
    private String password;

    public UserAuthenticationDTO(){
    }

    public String getPassword(){
        return password;
    }
}
