package be.plutus.api.dto.request;

import org.hibernate.validator.constraints.NotBlank;

public abstract class UserCreateDTO{

    @NotBlank( message = "{NotBlank.UserCreateDTO.firstName}" )
    private String firstName;

    @NotBlank( message = "{NotBlank.UserCreateDTO.lastName}" )
    private String lastName;

    @NotBlank( message = "{NotBlank.UserCreateDTO.password}" )
    private String password;

    public UserCreateDTO(){
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    @NotBlank( message = "{NotBlank.UserCreateDTO.username}" )
    public abstract String getUsername();

    public String getPassword(){
        return password;
    }
}
