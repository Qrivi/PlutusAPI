package be.plutus.api.dto.request;

import org.hibernate.validator.constraints.NotBlank;

public class PreferenceValueCreateDTO{

    @NotBlank( message = "{NotBlank.PreferenceValueCreateDTO.value}" )
    private String value;

    public PreferenceValueCreateDTO(){
    }

    public String getValue(){
        return value;
    }
}
