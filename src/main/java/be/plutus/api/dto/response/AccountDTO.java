package be.plutus.api.dto.response;

import be.plutus.core.model.currency.Currency;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.ZonedDateTime;

public class AccountDTO{

    private String email;
    private Currency currency;
    private ZonedDateTime created;

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

    public ZonedDateTime getCreated(){
        return created;
    }

    public void setCreated( ZonedDateTime created ){
        this.created = created;
    }

    @JsonFormat( pattern = "yyyy-MM-dd'T'HH:mm:ssZ" )
    public ZonedDateTime getCreatedISO8601(){
        return created;
    }
}
