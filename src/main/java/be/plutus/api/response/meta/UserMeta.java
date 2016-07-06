package be.plutus.api.response.meta;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class UserMeta extends AccountMeta{

    private String user;
    private Date updated;

    public UserMeta(){
    }

    public String getUser(){
        return user;
    }

    public void setUser( String user ){
        this.user = user; //User.username
    }

    public Date getUpdated(){
        return updated;
    }

    @JsonFormat( pattern = "yyyy-MM-dd'T'HH:mm:ssZ" )
    public Date getUpdatedISO8601(){
        return updated;
    }

    public void setUpdated( Date updated ){
        this.updated = updated;
    }
}
