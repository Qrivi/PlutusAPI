package be.plutus.api.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;

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
    private Date created;
    private Date expires;

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

    public Date getCreated(){
        return created;
    }

    public void setCreated( Date created ){
        this.created = created;
    }

    @JsonFormat( pattern = "yyyy-MM-dd'T'HH:mm:ssZ" )
    public Date getCreatedISO8601(){
        return created;
    }

    public Date getExpires(){
        return expires;
    }

    public void setExpires( Date expires ){
        this.expires = expires;
    }

    @JsonFormat( pattern = "yyyy-MM-dd'T'HH:mm:ssZ" )
    public Date getExpiresISO8601(){
        return expires;
    }

}
