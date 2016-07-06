package be.plutus.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Currency;
import java.util.Date;
import java.util.List;

public class AccountDTO{

    private String email;
    private Currency currency;
    private Date created;
    private List<SessionDTO> sessions;

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

    @JsonFormat( pattern = "yyyy-MM-dd'T'HH:mm:ssZ" )
    public Date getCreatedISO8601(){
        return created;
    }

    public void setCreated( Date created ){
        this.created = created;
    }

    public List<SessionDTO> getSessions(){
        return sessions;
    }

    public void setSessions( List<SessionDTO> sessions ){
        this.sessions = sessions;
    }
}
