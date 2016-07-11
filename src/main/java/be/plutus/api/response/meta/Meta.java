package be.plutus.api.response.meta;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Meta{

    protected Date requestTimestamp;
    protected int responseStatusCode;

    public Meta( Date requestTimestamp, int responseStatusCode ){
        this.requestTimestamp = requestTimestamp;
        this.responseStatusCode = responseStatusCode;
    }

    public Date getRequestTimestamp(){
        return requestTimestamp;
    }

    @JsonFormat( pattern = "yyyy-MM-dd'T'HH:mm:ssZ" )
    public Date getRequestTimestampISO8601(){
        return requestTimestamp;
    }

    public int getResponseStatusCode(){
        return responseStatusCode;
    }

    public static class Builder<B extends Meta.Builder<B>>{

        protected Date timestamp;
        protected int statusCode;

        public B success(){
            this.timestamp = new Date();
            this.statusCode = 200;
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
            return new Meta(timestamp, statusCode);
        }
    }
}
