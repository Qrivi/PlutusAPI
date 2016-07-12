package be.plutus.api.response;

import be.plutus.core.model.account.Account;
import be.plutus.core.model.currency.Currency;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude( JsonInclude.Include.NON_NULL)
public class Meta{

    private String user;
    private Date updated;

    private String account;
    private Currency currency;

    private Date requestTimestamp;
    private int responseStatusCode;

    public Meta( String user, Date updated, String account, Currency currency, Date requestTimestamp, int responseStatusCode ){
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

        private Date requestTimestamp;
        private int responseStatusCode;

        private Date timestamp;
        private int statusCode;

        public Builder user( String user, Date updated ){
            this.user = user;
            this.updated = updated;
            return this;
        }

        public Builder account( Account account ){
            this.account = account.getEmail();
            this.currency = account.getDefaultCurrency();
            return this;
        }

        public Builder success(){
            this.timestamp = new Date();
            this.statusCode = 200;
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
            return new Meta(user, updated, account, currency, timestamp, statusCode);
        }
    }
}
