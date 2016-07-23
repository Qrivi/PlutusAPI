package be.plutus.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;

@JsonPropertyOrder( {
        "status",
        "request",
        "requestISO8601"
} )
public class Meta{

    private int status;
    private Date request;

    Meta( int responseStatusCode, Date requestTimestamp ){
        this.request = requestTimestamp;
        this.status = responseStatusCode;
    }

    public int getStatus(){
        return status;
    }

    public Date getRequest(){
        return request;
    }

    @JsonFormat( pattern = "yyyy-MM-dd'T'HH:mm:ssZ" )
    public Date getRequestISO8601(){
        return request;
    }

    public static Meta success(){
        return new Meta.Builder().success().build();
    }

    public static Meta created(){
        return new Meta.Builder().created().build();
    }

    public static Meta badRequest(){
        return new Meta.Builder().badRequest().build();
    }

    public static Meta unauthorized(){
        return new Meta.Builder().unauthorized().build();
    }

    public static Meta forbidden(){
        return new Meta.Builder().forbidden().build();
    }

    public static Meta serverError(){
        return new Meta.Builder().serverError().build();
    }

    public static Meta notFound(){
        return new Meta.Builder().notFound().build();
    }

    public static class Builder<B extends Meta.Builder<B>>{

        protected Date timestamp;
        protected int statusCode;

        public B success(){
            this.timestamp = new Date();
            this.statusCode = 200;
            return (B)this;
        }

        public B created(){
            this.timestamp = new Date();
            this.statusCode = 201;
            return (B)this;
        }

        public B badRequest(){
            this.timestamp = new Date();
            this.statusCode = 400;
            return (B)this;
        }

        public B unauthorized(){
            this.timestamp = new Date();
            this.statusCode = 401;
            return (B)this;
        }

        public B forbidden(){
            this.timestamp = new Date();
            this.statusCode = 403;
            return (B)this;
        }

        public B serverError(){
            this.timestamp = new Date();
            this.statusCode = 500;
            return (B)this;
        }

        public B notFound(){
            this.timestamp = new Date();
            this.statusCode = 404;
            return (B)this;
        }

        public Meta build(){
            return new Meta(statusCode, timestamp);
        }
    }
}
