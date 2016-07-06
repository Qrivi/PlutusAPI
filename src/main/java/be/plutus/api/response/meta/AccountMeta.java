package be.plutus.api.response.meta;

import be.plutus.core.model.currency.Currency;

public class AccountMeta extends Meta{

    private String account;

    private Currency currency;

    public AccountMeta(){
    }

    public String getAccount(){
        return account;
    }

    public void setAccount( String account ){
        this.account = account; // Account.email
    }

    public Currency getCurrency(){
        return currency;
    }

    public void setCurrency( Currency currency ){
        this.currency = currency;
    }
}
