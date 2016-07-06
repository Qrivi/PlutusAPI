package be.plutus.api.response.meta;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class UserMeta extends AccountMeta{

    private String user;

    private Date updated;

    @JsonFormat( pattern = "yyyy-MM-dd'T'HH:mm:ssZ" )
    private Date updatedISO8601;

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

    public void setUpdated( Date updated ){
        this.updated = updated;
        this.updatedISO8601 = updated;
    }

    public Date getUpdatedISO8601(){
        return updatedISO8601;
    }
}
