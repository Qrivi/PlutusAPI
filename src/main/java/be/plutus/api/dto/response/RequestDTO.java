package be.plutus.api.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.ZonedDateTime;

public class RequestDTO{

    private String method;
    private String endpoint;
    private String ip;
    private ZonedDateTime timestamp;

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

    public ZonedDateTime getTimestamp(){
        return timestamp;
    }

    public void setTimestamp( ZonedDateTime timestamp ){
        this.timestamp = timestamp;
    }

    @JsonFormat( pattern = "yyyy-MM-dd'T'HH:mm:ssZ" )
    public ZonedDateTime getTimestampISO8601(){
        return timestamp;
    }
}
