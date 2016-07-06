package be.plutus.api.response.meta;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Meta{

    private Date requestTimestamp;
    private int responseStatusCode;

    public Date getRequestTimestamp(){
        return requestTimestamp;
    }

    @JsonFormat( pattern = "yyyy-MM-dd'T'HH:mm:ssZ" )
    public Date getRequestTimestampISO8601(){
        return requestTimestamp;
    }

    public void setRequestTimestamp( Date requestTimestamp ){
        this.requestTimestamp = requestTimestamp;
    }

    public int getResponseStatusCode(){
        return responseStatusCode;
    }

    public void setResponseStatusCode( int responseStatusCode ){
        this.responseStatusCode = responseStatusCode;
    }

    public static Meta success(){
        Meta meta = new Meta();
        meta.setResponseStatusCode( 200 );
        meta.setRequestTimestamp( new Date() );
        return meta;
    }

    public static Meta badRequest(){
        Meta meta = new Meta();
        meta.setResponseStatusCode( 400 );
        meta.setRequestTimestamp( new Date() );
        return meta;
    }

    public static Meta unauthorized(){
        Meta meta = new Meta();
        meta.setResponseStatusCode( 401 );
        meta.setRequestTimestamp( new Date() );
        return meta;
    }

    public static Meta forbidden(){
        Meta meta = new Meta();
        meta.setResponseStatusCode( 403 );
        meta.setRequestTimestamp( new Date() );
        return meta;
    }

    public static Meta serverError(){
        Meta meta = new Meta();
        meta.setResponseStatusCode( 500 );
        meta.setRequestTimestamp( new Date() );
        return meta;
    }

    public static Meta notFound(){
        Meta meta = new Meta();
        meta.setResponseStatusCode( 404 );
        meta.setRequestTimestamp( new Date() );
        return meta;
    }
}
