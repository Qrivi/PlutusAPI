package be.plutus.api.dto.response;

import be.plutus.core.model.transaction.TransactionType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.ZonedDateTime;

public class TransactionDTO{

    private int id;
    private String title;
    private String description;
    private double amount;
    private TransactionType type;
    private LocationDTO location;
    private ZonedDateTime timestamp;

    public TransactionDTO(){
    }

    public int getId(){
        return id;
    }

    public void setId( int id ){
        this.id = id;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle( String title ){
        this.title = title;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription( String description ){
        this.description = description;
    }

    public double getAmount(){
        return amount;
    }

    public void setAmount( double amount ){
        this.amount = amount;
    }

    public TransactionType getType(){
        return type;
    }

    public void setType( TransactionType type ){
        this.type = type;
    }

    public LocationDTO getLocation(){
        return location;
    }

    public void setLocation( LocationDTO location ){
        this.location = location;
    }

    public ZonedDateTime getTimestamp(){
        return timestamp;
    }

    public void setTimestamp( ZonedDateTime timestamp ){
        this.timestamp = timestamp;
    }

    @JsonFormat( pattern = "yyyy-MM-dd'T'HH:mm:ssZ" )
    public ZonedDateTime getTimestampISO8601(){
        return timestamp;
    }
}
