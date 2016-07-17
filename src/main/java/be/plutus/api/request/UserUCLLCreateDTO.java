package be.plutus.api.request;

import org.hibernate.validator.constraints.NotBlank;

public class UserUCLLCreateDTO{

    @NotBlank( message = "{NotBlank.UserUCLLCreateDTO.firstName}" )
    private String firstName;

    @NotBlank( message = "{NotBlank.UserUCLLCreateDTO.lastName}" )
    private String lastName;

    @NotBlank( message = "{NotBlank.UserUCLLCreateDTO.username}" )
    private String username;

    @NotBlank( message = "{NotBlank.UserUCLLCreateDTO.password}" )
    private String password;

    public UserUCLLCreateDTO(){
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }
}
