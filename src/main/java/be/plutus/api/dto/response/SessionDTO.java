package be.plutus.api.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.ZonedDateTime;

@JsonPropertyOrder( {
        "index",
        "application",
        "device",
        "ip",
        "created",
        "createdISO8601",
        "expires",
        "expiresISO8601"
} )
public class SessionDTO{

    private int index;
    private String application;
    private String device;
    private String ip;
    private ZonedDateTime created;
    private ZonedDateTime expires;

    public SessionDTO(){
    }

    public int getIndex(){
        return index;
    }

    public void setIndex( int index ){
        this.index = index;
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

    public String getIp(){
        return ip;
    }

    public void setIp( String ip ){
        this.ip = ip;
    }

    public ZonedDateTime getCreated(){
        return created;
    }

    public void setCreated( ZonedDateTime created ){
        this.created = created;
    }

    @JsonFormat( pattern = "yyyy-MM-dd'T'HH:mm:ssZ" )
    public ZonedDateTime getCreatedISO8601(){
        return created;
    }

    public ZonedDateTime getExpires(){
        return expires;
    }

    public void setExpires( ZonedDateTime expires ){
        this.expires = expires;
    }

    @JsonFormat( pattern = "yyyy-MM-dd'T'HH:mm:ssZ" )
    public ZonedDateTime getExpiresISO8601(){
        return expires;
    }

}
