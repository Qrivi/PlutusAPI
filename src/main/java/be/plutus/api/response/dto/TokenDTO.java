package be.plutus.api.response.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class TokenDTO{

    private String token;
    private String application;
    private String device;
    private Date expires;

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

    public Date getExpires(){
        return expires;
    }

    @JsonFormat( pattern = "yyyy-MM-dd'T'HH:mm:ssZ" )
    public Date getExpiresISO8601(){
        return expires;
    }

    public void setExpires( Date expires ){
        this.expires = expires;
    }
}
