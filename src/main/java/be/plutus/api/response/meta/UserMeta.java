package be.plutus.api.response.meta;

import be.plutus.core.model.currency.Currency;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class UserMeta extends AccountMeta{

    private String user;
    private Date updated;

    public UserMeta( Date requestTimestamp, int responseStatusCode, String account, Currency currency, String user, Date updated ){
        super( requestTimestamp, responseStatusCode, account, currency );
        this.user = user;
        this.updated = updated;
    }

    public String getUser(){
        return user;
    }

    public Date getUpdated(){
        return updated;
    }

    @JsonFormat( pattern = "yyyy-MM-dd'T'HH:mm:ssZ" )
    public Date getUpdatedISO8601(){
        return updated;
    }

    public static class Builder extends AccountMeta.Builder{

        private String user;
        private Date updated;

        public Builder user( String user ){
            this.user = user;
            return this;
        }

        public Builder currency( Date updated ){
            this.updated = updated;
            return this;
        }

        public Meta build(){
            return new UserMeta(timestamp, statusCode, account, currency, user, updated);
        }
    }
}
