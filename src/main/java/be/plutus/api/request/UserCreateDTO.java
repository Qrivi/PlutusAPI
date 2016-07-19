package be.plutus.api.request;

import be.plutus.api.validation.CorrectUsername;
import be.plutus.api.validation.SupportedInstitution;
import org.hibernate.validator.constraints.NotBlank;

public class UserCreateDTO{

    @NotBlank( message = "{NotBlank.UserCreateDTO.firstName}" )
    private String firstName;

    @NotBlank( message = "{NotBlank.UserCreateDTO.lastName}" )
    private String lastName;

    @NotBlank( message = "{NotBlank.UserCreateDTO.institution}" )
    @SupportedInstitution( message = "{SupportedInstitution.UserCreateDTO.institution}" )
    private String institution;

    //TODO fix this
    // The idea was to pass the institution from right above to the @CorrectUsername annotation, but annotation can only
    // take constants, no variables, unfortunately.
    @NotBlank( message = "{NotBlank.UserCreateDTO.username}" )
    @CorrectUsername( institution = "ucll", message = "{CorrectUsername.UserCreateDTO.username}" )
    private String username;

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

    public String getInstitution(){
        return institution;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }
}
