package be.plutus.api.response;

import be.plutus.core.service.DateService;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.ZonedDateTime;

@JsonPropertyOrder( {
        "status",
        "request",
        "requestISO8601"
} )
public class Meta{

    private int status;
    private ZonedDateTime request;

    Meta( int status, ZonedDateTime requestTimestamp ){
        this.status = status;
        this.request = requestTimestamp;
    }

    public int getStatus(){
        return status;
    }

    public ZonedDateTime getRequest(){
        return request;
    }

    @JsonFormat( pattern = "yyyy-MM-dd'T'HH:mm:ssZ" )
    public ZonedDateTime getRequestISO8601(){
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

    public static Meta forbidden(){
        return new Meta.Builder().forbidden().build();
    }

    public static Meta notFound(){
        return new Meta.Builder().notFound().build();
    }

    public static class Builder<B extends Meta.Builder<B>>{

        protected int status;
        protected ZonedDateTime timestamp;

        public B success(){
            this.timestamp = DateService.now();
            this.status = 200;
            return (B)this;
        }

        public B created(){
            this.timestamp = DateService.now();
            this.status = 201;
            return (B)this;
        }

        public B badRequest(){
            this.timestamp = DateService.now();
            this.status = 400;
            return (B)this;
        }

        public B unauthorized(){
            this.timestamp = DateService.now();
            this.status = 401;
            return (B)this;
        }

        public B forbidden(){
            this.timestamp = DateService.now();
            this.status = 403;
            return (B)this;
        }

        public B serverError(){
            this.timestamp = DateService.now();
            this.status = 500;
            return (B)this;
        }

        public B notFound(){
            this.timestamp = DateService.now();
            this.status = 404;
            return (B)this;
        }

        public Meta build(){
            return new Meta( status, timestamp );
        }
    }
}
