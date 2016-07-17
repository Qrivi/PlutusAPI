package be.plutus.api.endpoint;

import be.plutus.api.request.UserRemoveDTO;
import be.plutus.api.request.UserUCLLCreateDTO;
import be.plutus.api.response.CreditDTO;
import be.plutus.api.response.InstitutionDTO;
import be.plutus.api.response.Response;
import be.plutus.api.response.UserDTO;
import be.plutus.api.security.context.SecurityContext;
import be.plutus.core.model.account.Account;
import be.plutus.core.model.account.Credit;
import be.plutus.core.model.account.User;
import be.plutus.core.model.currency.Currency;
import be.plutus.core.model.currency.CurrencyConverter;
import be.plutus.core.model.location.Institution;
import be.plutus.core.service.AccountService;
import be.plutus.core.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(
        path = "/account/users",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE )
public class UsersEndpoint{

    @Autowired
    AccountService accountService;

    @Autowired
    LocationService locationService;

    //region GET /account/users

    @RequestMapping( method = RequestMethod.GET )
    public ResponseEntity<Response> get(){

        Account account = accountService.getAccount( SecurityContext.getAccount().getId() );

        final int[] index = {0};
        List<UserDTO> users = account.getUsers()
                .stream()
                .map( user -> {
                    Institution institution = user.getInstitution();

                    InstitutionDTO institutionDTO = new InstitutionDTO();
                    institutionDTO.setName( institution.getName() );
                    institutionDTO.setSlur( institution.getSlur() );

                    UserDTO dto = new UserDTO();
                    dto.setIndex( index[0]++ );
                    dto.setUsername( user.getUsername() );
                    dto.setFirstName( user.getFirstName() );
                    dto.setLastName( user.getLastName() );
                    dto.setInstitution( institutionDTO );
                    dto.setCreated( user.getCreationDate() );
                    dto.setUpdated( user.getFetchDate() );

                    return dto;
                } ).collect( Collectors.toList() );


        Response response = new Response.Builder()
                .account( account )
                .data( users )
                .success()
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    //endregion

    //region DELETE /account/users

    @RequestMapping( method = RequestMethod.DELETE )
    public ResponseEntity<Response> delete( @Valid @RequestBody UserRemoveDTO dto, BindingResult result ){

        if( result.hasErrors() )
            return EndpointUtils.createErrorResponse( result );

        accountService.removeAllUsersFromAccount( SecurityContext.getAccount().getId() );

        Response response = new Response.Builder()
                .success()
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    //endregion

    //region POST /account/users?institution=ucll

    @RequestMapping( params = { "institution=ucll" }, method = RequestMethod.POST )
    public ResponseEntity<Response> post( @Valid @RequestBody UserUCLLCreateDTO dto, BindingResult result ){

        if( result.hasErrors() )
            return EndpointUtils.createErrorResponse( result );

        accountService.createUser( SecurityContext.getAccount().getId(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getUsername(),
                dto.getPassword(),
                locationService.getInstitutionBySlur( "ucll" ) );

        Response response = new Response.Builder()
                .created()
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    //endregion

    //region GET /account/users/{index}

    @RequestMapping( value = "/{index}" ,method = RequestMethod.GET )
    public ResponseEntity<Response> getByIndex( @PathVariable( "index" ) int index ){

        Account account = accountService.getAccount( SecurityContext.getAccount().getId() );
        List<User> users = account.getUsers();

        if( index < 0 || index > users.size() - 1 )
            return new ResponseEntity<>( new Response.Builder().notFound().build(), HttpStatus.NOT_FOUND );

        User user = users.get( index );

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

        Response response = new Response.Builder()
                .account( account )
                .data( dto )
                .success()
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    //endregion

    //region GET /account/users/{index}/credit

    @RequestMapping( value = "/{index}/credit" ,method = RequestMethod.GET )
    public ResponseEntity<Response> getCreditByIndex( @PathVariable( "index" ) int index , @RequestParam( name = "currency", required = false ) String currencyName ){

        Account account = accountService.getAccount( SecurityContext.getAccount().getId() );
        List<User> users = account.getUsers();

        if( index < 0 || index > users.size() - 1 )
            return new ResponseEntity<>( new Response.Builder().notFound().build(), HttpStatus.NOT_FOUND );

        User user = users.get( index );
        Credit credit = user.getCredit();

        Currency currency = Currency.getFromName( currencyName );

        if (currency == null)
            currency = account.getDefaultCurrency();

        CreditDTO dto = new CreditDTO();
        dto.setAmount( CurrencyConverter.convert( credit.getAmount(), credit.getCurrency(), currency ) );

        Response response = new Response.Builder()
                .account( account )
                .user( user )
                .currency( currency )
                .data( dto )
                .success()
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    //endregion

    //region DELETE /account/users/{index}

    @RequestMapping( value = "/{index}" ,method = RequestMethod.DELETE )
    public ResponseEntity<Response> deleteByIndex( @PathVariable( "index" ) int index, @Valid @RequestBody UserRemoveDTO dto, BindingResult result ){

        if( result.hasErrors() )
            return EndpointUtils.createErrorResponse( result );

        accountService.removeUserFromAccount( SecurityContext.getAccount().getId(), index );

        Response response = new Response.Builder()
                .success()
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    //endregion
}
