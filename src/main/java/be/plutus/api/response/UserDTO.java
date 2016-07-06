package be.plutus.api.response;

import be.plutus.core.model.location.Institution;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class UserDTO{

    private int index;
    private String firstName;
    private String lastName;
    private String username;
    private Institution institution;
    private Date updated;

    public UserDTO(){
    }

    public int getIndex(){
        return index;
    }

    public void setIndex( int index ){
        this.index = index;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName( String firstName ){
        this.firstName = firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName( String lastName ){
        this.lastName = lastName;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername( String username ){
        this.username = username;
    }

    public Institution getInstitution(){
        return institution;
    }

    public void setInstitution( Institution institution ){
        this.institution = institution;
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
