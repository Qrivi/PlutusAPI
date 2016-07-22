package be.plutus.api.dto.request;

import be.plutus.api.validation.CorrectUsername;

public class UserUCLLCreateDTO extends UserCreateDTO{

    @CorrectUsername( institution = "ucll", message = "{CorrectUsername.UserUCLLCreateDTO.username}" )
    private String username;

    public UserUCLLCreateDTO(){
    }

    @Override
    public String getUsername(){
        return username;
    }
}
