package be.plutus.api.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.ZonedDateTime;

public class TokenDTO{

    private String token;
    private String application;
    private String device;
    private ZonedDateTime expires;

    public TokenDTO(){
    }

    public String getToken(){
        return token;
    }

    public void setToken( String token ){
        this.token = token;
    }

    public String getApplication(){
        return application;
    }

    public void setApplication( String application ){
        this.application = application;
    }

    public String getDevice(){
        return device;
    }

    public void setDevice( String device ){
        this.device = device;
    }

    public ZonedDateTime getExpires(){
        return expires;
    }

    public void setExpires( ZonedDateTime expires ){
        this.expires = expires;
    }

    @JsonFormat( shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ" )
    public ZonedDateTime getExpiresISO8601(){
        return expires;
    }
}
