package be.plutus.api.converter;

import be.plutus.api.response.*;
import be.plutus.core.model.account.Account;
import be.plutus.core.model.account.User;
import be.plutus.core.model.currency.Currency;
import be.plutus.core.model.currency.CurrencyConverter;
import be.plutus.core.model.location.Campus;
import be.plutus.core.model.location.Institution;
import be.plutus.core.model.location.Location;
import be.plutus.core.model.token.Request;
import be.plutus.core.model.token.Token;
import be.plutus.core.model.transaction.Transaction;

public class Converter{

    public static AccountDTO convert( Account account ){
        AccountDTO dto = new AccountDTO();
        dto.setEmail( account.getEmail() );
        dto.setCurrency( account.getDefaultCurrency() );
        dto.setCreated( account.getCreationDate() );
        return dto;
    }

    public static UserDTO convert( User user, int index ){
        Institution institution = user.getInstitution();

        InstitutionDTO institutionDTO = new InstitutionDTO();
        institutionDTO.setName( institution.getName() );
        institutionDTO.setSlur( institution.getSlur() );

        UserDTO dto = new UserDTO();
        dto.setIndex( index );
        dto.setUsername( user.getUsername() );
        dto.setFirstName( user.getFirstName() );
        dto.setLastName( user.getLastName() );
        dto.setInstitution( institutionDTO );
        dto.setCreated( user.getCreationDate() );
        dto.setUpdated( user.getFetchDate() );
        return dto;
    }

    public static TokenDTO convert( Token token ){
        TokenDTO dto = new TokenDTO();
        dto.setToken( token.getToken() );
        dto.setApplication( token.getApplicationName() );
        dto.setDevice( token.getDeviceName() );
        dto.setExpires( token.getExpiryDate() );
        return dto;
    }

    public static InstitutionWithHintDTO convert( Institution institution ){
        InstitutionWithHintDTO dto = new InstitutionWithHintDTO();
        dto.setName( institution.getName() );
        dto.setSlur( institution.getSlur() );
        dto.setHint( institution.getHint() );
        return dto;
    }

    public static SessionDTO convert( Token token , int index ){
        SessionDTO dto = new SessionDTO();
        dto.setIndex( index );
        dto.setApplication( token.getApplicationName() );
        dto.setDevice( token.getDeviceName() );
        dto.setIp( token.getRequestIp() );
        dto.setCreated( token.getCreationDate() );
        dto.setExpires( token.getExpiryDate() );
        return dto;
    }

    public static RequestDTO convert( Request request ){
        RequestDTO dto = new RequestDTO();
        dto.setMethod( request.getMethod() );
        dto.setEndpoint( request.getEndpoint() );
        dto.setIp( request.getIp() );
        dto.setTimestamp( request.getTimestamp() );
        return dto;
    }

    public static TransactionDTO convert( Transaction transaction , Currency currency ){
        Location location = transaction.getLocation();
        Campus campus = location.getCampus();
        Institution institution = campus.getInstitution();

        InstitutionDTO institutionDTO = new InstitutionDTO();
        institutionDTO.setName( institution.getName() );
        institutionDTO.setSlur( institution.getSlur() );

        CampusDTO campusDTO = new CampusDTO();
        campusDTO.setAddress( campus.getAddress() );
        campusDTO.setCity( campus.getCity() );
        campusDTO.setCountry( campus.getCountry() );
        campusDTO.setInstitution( institutionDTO );
        campusDTO.setLat( campus.getLat() );
        campusDTO.setLng( campus.getLng() );
        campusDTO.setName( campus.getName() );
        campusDTO.setZip( campus.getZip() );

        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setName( location.getName() );
        locationDTO.setLng( location.getLng() );
        locationDTO.setLat( location.getLat() );
        locationDTO.setCampus( campusDTO );

        TransactionDTO dto = new TransactionDTO();
        dto.setId( transaction.getId() );
        dto.setAmount( CurrencyConverter.convert( transaction.getAmount(), transaction.getCurrency(), currency ) );
        dto.setTitle( transaction.getTitle() );
        dto.setDescription( transaction.getDescription() );
        dto.setType( transaction.getType() );
        dto.setLocation( locationDTO );
        dto.setTimestamp( transaction.getTimestamp() );
        return dto;
    }
}
