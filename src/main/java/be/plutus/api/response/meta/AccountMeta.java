package be.plutus.api.response.meta;

import be.plutus.core.model.currency.Currency;

import java.util.Date;

public class AccountMeta extends Meta{

    protected String account;
    protected Currency currency;

    public AccountMeta( Date requestTimestamp, int responseStatusCode, String account, Currency currency ){
        super( requestTimestamp, responseStatusCode );
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
            return new AccountMeta(timestamp, statusCode, account, currency);
        }
    }
}
