package be.plutus.api.response.meta;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class DefaultMeta{

    @JsonFormat( pattern = "yyyy-MM-dd'T'HH:mm:ssZ" )
    private Date requestTimestamp;
    private int responseStatusCode;

    public static DefaultMeta success(){
        DefaultMeta meta = new DefaultMeta();
        meta.setResponseStatusCode( 200 );
        meta.setRequestTimestamp( new Date() );
        return meta;
    }

    public static DefaultMeta badRequest(){
        DefaultMeta meta = new DefaultMeta();
        meta.setResponseStatusCode( 400 );
        meta.setRequestTimestamp( new Date() );
        return meta;
    }

    public static DefaultMeta unauthorized(){
        DefaultMeta meta = new DefaultMeta();
        meta.setResponseStatusCode( 401 );
        meta.setRequestTimestamp( new Date() );
        return meta;
    }

    public static DefaultMeta forbidden(){
        DefaultMeta meta = new DefaultMeta();
        meta.setResponseStatusCode( 403 );
        meta.setRequestTimestamp( new Date() );
        return meta;
    }

    public static DefaultMeta serverError(){
        DefaultMeta meta = new DefaultMeta();
        meta.setResponseStatusCode( 500 );
        meta.setRequestTimestamp( new Date() );
        return meta;
    }

    public static DefaultMeta notFound(){
        DefaultMeta meta = new DefaultMeta();
        meta.setResponseStatusCode( 404 );
        meta.setRequestTimestamp( new Date() );
        return meta;
    }

    public int getResponseStatusCode(){
        return responseStatusCode;
    }

    public void setResponseStatusCode( int responseStatusCode ){
        this.responseStatusCode = responseStatusCode;
    }

    public Date getRequestTimestamp(){
        return requestTimestamp;
    }

    public void setRequestTimestamp( Date requestTimestamp ){
        this.requestTimestamp = requestTimestamp;
    }
}
