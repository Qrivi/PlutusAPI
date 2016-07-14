package be.plutus.api.response.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

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

    @JsonFormat( pattern = "yyyy-MM-dd'T'HH:mm:ssZ" )
    public Date getCreatedISO8601(){
        return created;
    }

    public void setCreated( Date created ){
        this.created = created;
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
