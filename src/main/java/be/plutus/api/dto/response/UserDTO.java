package be.plutus.api.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.ZonedDateTime;

@JsonPropertyOrder( {
        "index",
        "firstName",
        "lastName",
        "username",
        "institution",
        "created",
        "createdISO8601",
        "updated",
        "updatedISO8601"
} )
public class UserDTO{

    private int index;
    private String firstName;
    private String lastName;
    private String username;
    private InstitutionDTO institution;
    private ZonedDateTime created;
    private ZonedDateTime updated;

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

    public ZonedDateTime getUpdated(){
        return updated;
    }

    public void setUpdated( ZonedDateTime updated ){
        this.updated = updated;
    }

    @JsonFormat( pattern = "yyyy-MM-dd'T'HH:mm:ssZ" )
    public ZonedDateTime getUpdatedISO8601(){
        return updated;
    }
}
