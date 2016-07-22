package be.plutus.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class UserDTO{

    private int index;
    private String firstName;
    private String lastName;
    private String username;
    private InstitutionDTO institution;
    private Date created;
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

    public InstitutionDTO getInstitution(){
        return institution;
    }

    public void setInstitution( InstitutionDTO institution ){
        this.institution = institution;
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

    public Date getUpdated(){
        return updated;
    }

    public void setUpdated( Date updated ){
        this.updated = updated;
    }

    @JsonFormat( pattern = "yyyy-MM-dd'T'HH:mm:ssZ" )
    public Date getUpdatedISO8601(){
        return updated;
    }
}
