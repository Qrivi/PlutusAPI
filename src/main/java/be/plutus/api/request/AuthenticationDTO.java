package be.plutus.api.request;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class AuthenticationDTO{

    @NotBlank( message = "{NotBlank.AuthenticationDTO.email}" )
    @Email( message = "{Email.AuthenticationDTO.email}" )
    private String email;

    @NotBlank( message = "{NotBlank.AuthenticationDTO.password}" )
    private String password;

    @NotBlank( message = "{NotBlank.AuthenticationDTO.application}" )
    private String application;

    @NotBlank( message = "{NotBlank.AuthenticationDTO.device}" )
    private String device;

    public AuthenticationDTO(){
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

    public String getApplication(){
        return application;
    }

    public String getDevice(){
        return device;
    }
}
