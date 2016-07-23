package be.plutus.api.response;

import be.plutus.core.model.currency.Currency;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;

@JsonPropertyOrder( {
        "user",
        "updated",
        "updatedISO8601",
} )
public class UserMeta extends AccountMeta{

    private String user;
    private Date updated;

    UserMeta( int responseStatusCode, Date requestTimestamp, String account, Currency currency, String user, Date updated ){
        super( responseStatusCode, requestTimestamp, account, currency );
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

        public Builder updated( Date updated ){
            this.updated = updated;
            return this;
        }

        public Meta build(){
            return new UserMeta(statusCode, timestamp, account, currency, user, updated);
        }
    }
}