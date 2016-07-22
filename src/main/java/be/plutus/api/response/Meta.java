package be.plutus.api.response;

import be.plutus.core.model.currency.Currency;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;

@JsonPropertyOrder( {
        "user",
        "updated",
        "account",
        "currency",
        "responseStatusCode",
        "requestTimestamp",
        "requestTimestampISO8601",
} )
@JsonInclude( JsonInclude.Include.NON_NULL )
public class Meta{

    private String user;
    private Date updated;

    private String account;
    private Currency currency;

    private Date requestTimestamp;
    private int responseStatusCode;

    private Meta( String user, Date updated, String account, Currency currency, Date requestTimestamp, int responseStatusCode ){
        this.user = user;
        this.updated = updated;
        this.account = account;
        this.currency = currency;
        this.requestTimestamp = requestTimestamp;
        this.responseStatusCode = responseStatusCode;
    }

    public String getUser(){
        return user;
    }

    public Date getUpdated(){
        return updated;
    }

    public String getAccount(){
        return account;
    }

    public Currency getCurrency(){
        return currency;
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

    public static class Builder{

        private String user;
        private Date updated;

        private String account;
        private Currency currency;

        private Date timestamp;
        private int statusCode;

        public Builder user( String user ){
            this.user = user;
            return this;
        }

        public Builder updated( Date updated ){
            this.updated = updated;
            return this;
        }

        public Builder account( String email ){
            this.account = email;
            return this;
        }

        public Builder currency( Currency currency ){
            this.currency = currency;
            return this;
        }

        public Builder success(){
            this.timestamp = new Date();
            this.statusCode = 200;
            return this;
        }

        public Builder created(){
            this.timestamp = new Date();
            this.statusCode = 201;
            return this;
        }

        public Builder badRequest(){
            this.timestamp = new Date();
            this.statusCode = 400;
            return this;
        }

        public Builder unauthorized(){
            this.timestamp = new Date();
            this.statusCode = 401;
            return this;
        }

        public Builder forbidden(){
            this.timestamp = new Date();
            this.statusCode = 403;
            return this;
        }

        public Builder serverError(){
            this.timestamp = new Date();
            this.statusCode = 500;
            return this;
        }

        public Builder notFound(){
            this.timestamp = new Date();
            this.statusCode = 404;
            return this;
        }

        public Meta build(){
            return new Meta( user, updated, account, currency, timestamp, statusCode );
        }
    }
}
