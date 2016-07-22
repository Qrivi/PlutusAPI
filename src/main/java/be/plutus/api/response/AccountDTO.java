package be.plutus.api.response;

import be.plutus.core.model.currency.Currency;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class AccountDTO{

    private String email;
    private Currency currency;
    private Date created;

    public AccountDTO(){
    }

    public String getEmail(){
        return email;
    }

    public void setEmail( String email ){
        this.email = email;
    }

    public Currency getCurrency(){
        return currency;
    }

    public void setCurrency( Currency currency ){
        this.currency = currency;
    }

    public Date getCreated(){
        return created;
    }

    public void setCreated( Date created ){
        this.created = created;
    }

    @JsonFormat( pattern = "yyyy-MM-dd'T'HH:mm:ssZ" )
    public Date getCreatedISO8601(){
        return created;
    }
}
