package be.plutus.api.request;

import org.hibernate.validator.constraints.NotBlank;

public class UserUpdateDTO extends UserAuthenticationDTO{

    @NotBlank( message = "{NotBlank.UserUpdateDTO.newPassword}" )
    private String newPassword;

    public UserUpdateDTO(){
    }

    public String getNewPassword(){
        return newPassword;
    }
}
