package be.plutus.api.response;

import be.plutus.core.model.currency.Currency;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;

@JsonPropertyOrder( {
        "status",
        "request",
        "requestISO8601",
        "account",
        "currency"
} )
public class AccountMeta extends Meta{

    private String account;
    private Currency currency;

    AccountMeta( int responseStatusCode, Date requestTimestamp, String account, Currency currency ){
        super( responseStatusCode, requestTimestamp );
        this.account = account;
        this.currency = currency;
    }

    public String getAccount(){
        return account;
    }

    public Currency getCurrency(){
        return currency;
    }

    public static class Builder<B extends AccountMeta.Builder<B>> extends Meta.Builder<B>{

        protected String account;
        protected Currency currency;

        public B account( String account ){
            this.account = account;
            return (B)this;
        }

        public B currency( Currency currency ){
            this.currency = currency;
            return (B)this;
        }

        public Meta build(){
            return new AccountMeta(statusCode, timestamp, account, currency);
        }
    }
}
