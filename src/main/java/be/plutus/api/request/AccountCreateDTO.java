package be.plutus.api.request;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class AccountCreateDTO{

    @NotBlank( message = "{NotBlank.AccountCreateDTO.email}" )
    @Email( message = "{Email.AccountCreateDTO.email}" )
    private String email;

    @NotBlank( message = "{NotBlank.AccountCreateDTO.password}" )
    private String password;

    public AccountCreateDTO(){
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }
}
