package be.plutus.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class RequestDTO{

    private String method;
    private String endpoint;
    private String ip;
    private Date timestamp;

    public RequestDTO(){
    }

    public String getMethod(){
        return method;
    }

    public void setMethod( String method ){
        this.method = method;
    }

    public String getEndpoint(){
        return endpoint;
    }

    public void setEndpoint( String endpoint ){
        this.endpoint = endpoint;
    }

    public String getIp(){
        return ip;
    }

    public void setIp( String ip ){
        this.ip = ip;
    }

    public Date getTimestamp(){
        return timestamp;
    }

    @JsonFormat( pattern = "yyyy-MM-dd'T'HH:mm:ssZ" )
    public Date getTimestampISO8601(){
        return timestamp;
    }

    public void setTimestamp( Date timestamp ){
        this.timestamp = timestamp;
    }
}
