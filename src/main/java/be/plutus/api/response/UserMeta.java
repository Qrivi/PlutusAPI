package be.plutus.api.response;

import be.plutus.core.model.currency.Currency;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.ZonedDateTime;

@JsonPropertyOrder( {
        "status",
        "request",
        "requestISO8601",
        "account",
        "currency",
        "user",
        "updated",
        "updatedISO8601"
} )
public class UserMeta extends AccountMeta{

    private String user;
    private ZonedDateTime updated;

    UserMeta( int status, ZonedDateTime request, String account, Currency currency, String user, ZonedDateTime updated ){
        super( status, request, account, currency );
        this.user = user;
        this.updated = updated;
    }

    public String getUser(){
        return user;
    }

    public ZonedDateTime getUpdated(){
        return updated;
    }

    @JsonFormat( pattern = "yyyy-MM-dd'T'HH:mm:ssZ" )
    public ZonedDateTime getUpdatedISO8601(){
        return updated;
    }

    public static class Builder extends AccountMeta.Builder{

        private String user;
        private ZonedDateTime updated;

        public Builder user( String user ){
            this.user = user;
            return this;
        }

        public Builder updated( ZonedDateTime updated ){
            this.updated = updated;
            return this;
        }

        public Meta build(){
            return new UserMeta( status, timestamp, account, currency, user, updated);
        }
    }
}