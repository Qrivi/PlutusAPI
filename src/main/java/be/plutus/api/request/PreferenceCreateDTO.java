package be.plutus.api.request;

import org.hibernate.validator.constraints.NotBlank;

public class PreferenceCreateDTO extends PreferenceValueCreateDTO{

    @NotBlank( message = "{NotBlank.PreferenceCreateDTO.key}" )
    private String key;

    public PreferenceCreateDTO(){
    }

    public String getKey(){
        return key;
    }
}
