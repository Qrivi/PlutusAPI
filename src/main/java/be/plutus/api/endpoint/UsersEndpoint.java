package be.plutus.api.endpoint;

import be.plutus.api.request.UserAuthenticationDTO;
import be.plutus.api.request.UserCreateDTO;
import be.plutus.api.request.UserUCLLCreateDTO;
import be.plutus.api.request.UserUpdateDTO;
import be.plutus.api.response.*;
import be.plutus.api.security.context.SecurityContext;
import be.plutus.core.model.account.Account;
import be.plutus.core.model.account.Credit;
import be.plutus.core.model.account.User;
import be.plutus.core.model.currency.Currency;
import be.plutus.core.model.currency.CurrencyConverter;
import be.plutus.core.model.location.Institution;
import be.plutus.core.model.transaction.Transaction;
import be.plutus.core.model.transaction.TransactionType;
import be.plutus.core.service.AccountService;
import be.plutus.core.service.LocationService;
import be.plutus.core.service.TransactionService;
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
    TransactionService transactionService;

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
    public ResponseEntity<Response> delete( @Valid @RequestBody UserAuthenticationDTO dto, BindingResult result ){

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

    //region PUT /account/users/{index}

    @RequestMapping( value = "/{index}" ,method = RequestMethod.PUT )
    public ResponseEntity<Response> putByIndex( @PathVariable( "index" ) int index, @Valid @RequestBody UserUpdateDTO dto, BindingResult result ){

        if( result.hasErrors() )
            return EndpointUtils.createErrorResponse( result );

        Account account = accountService.getAccount( SecurityContext.getAccount().getId() );
        List<User> users = account.getUsers();

        if( index < 0 || index > users.size() - 1 )
            return new ResponseEntity<>( new Response.Builder().notFound().build(), HttpStatus.NOT_FOUND );

        User user = users.get( index );

        accountService.updateUser( user.getId(), dto.getNewPassword() );

        Response response = new Response.Builder()
                .success()
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    //endregion

    //region POST /account/users/{index}/reset

    @RequestMapping( value = "/{index}/reset" ,method = RequestMethod.POST )
    public ResponseEntity<Response> resetByIndex( @PathVariable( "index" ) int index, @Valid @RequestBody UserAuthenticationDTO dto, BindingResult result ){

        if( result.hasErrors() )
            return EndpointUtils.createErrorResponse( result );

        Account account = accountService.getAccount( SecurityContext.getAccount().getId() );
        List<User> users = account.getUsers();

        if( index < 0 || index > users.size() - 1 )
            return new ResponseEntity<>( new Response.Builder().notFound().build(), HttpStatus.NOT_FOUND );

        User user = users.get( index );

        accountService.resetTransactionsFromUser( user.getId() );

        Response response = new Response.Builder()
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

    //region GET /account/users/{index}/transactions

    @RequestMapping( value = "/{index}/transactions" ,method = RequestMethod.GET )
    public ResponseEntity<Response> getTransactionsByIndex( @PathVariable( "index" ) int index,
                                                            @RequestParam( name = "currency", required = false ) String currencyName,
                                                            @RequestParam( name = "limit", required = false ) int limit,
                                                            @RequestParam( name = "limit", required = false ) int offset,
                                                            @RequestParam( name = "type", required = false ) String typeName ){

        Account account = accountService.getAccount( SecurityContext.getAccount().getId() );
        List<User> users = account.getUsers();

        if( index < 0 || index > users.size() - 1 )
            return new ResponseEntity<>( new Response.Builder().notFound().build(), HttpStatus.NOT_FOUND );

        User user = users.get( index );

        Currency currency = Currency.getFromName( currencyName );
        currency = (currency == null) ? account.getDefaultCurrency() : currency;

        TransactionType type = TransactionType.getFromType( typeName );

        List<Transaction> transactions;

        if (type == null)
            transactions = transactionService.getTransactionByUser( user.getId(), limit, offset );
        else
            transactions = transactionService.getTransactionByUserAndType( user.getId(), type, limit, offset );

        Currency finalCurrency = currency;
        List<TransactionDTO> transactionDTOs = transactions
                .stream()
                .map( transaction -> {
                    TransactionDTO dto = new TransactionDTO();
                    dto.setId( transaction.getId() );
                    dto.setAmount( CurrencyConverter.convert( transaction.getAmount(), transaction.getCurrency(), finalCurrency ) );
                    dto.setTitle( transaction.getTitle() );
                    dto.setDescription( transaction.getDescription() );
                    dto.setType( transaction.getType() );
                    dto.setLocation( transaction.getLocation() );
                    dto.setTimestamp( transaction.getTimestamp() );
                    return dto;
        } ).collect( Collectors.toList() );

        Response response = new Response.Builder()
                .account( account )
                .currency( currency )
                .data( transactionDTOs )
                .success()
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    //endregion

    //region GET /account/users/{index}/transactions/{id}

    @RequestMapping( value = "/{index}/transactions/{id}" ,method = RequestMethod.GET )
    public ResponseEntity<Response> getTransactionsByIndex( @PathVariable( "index" ) int index,
                                                            @PathVariable( "id" ) int id,
                                                            @RequestParam( name = "currency", required = false ) String currencyName ){

        Account account = accountService.getAccount( SecurityContext.getAccount().getId() );
        List<User> users = account.getUsers();

        if( index < 0 || index > users.size() - 1 )
            return new ResponseEntity<>( new Response.Builder().notFound().build(), HttpStatus.NOT_FOUND );

        User user = users.get( index );

        Currency currency = Currency.getFromName( currencyName );
        currency = (currency == null) ? account.getDefaultCurrency() : currency;

        Transaction transaction = transactionService.getTransaction( id );

        if( transaction.getUser().getId() != user.getId() )
            return new ResponseEntity<>( new Response.Builder().notFound().build(), HttpStatus.NOT_FOUND );

        TransactionDTO dto = new TransactionDTO();
        dto.setId( transaction.getId() );
        dto.setAmount( CurrencyConverter.convert( transaction.getAmount(), transaction.getCurrency(), currency ) );
        dto.setTitle( transaction.getTitle() );
        dto.setDescription( transaction.getDescription() );
        dto.setType( transaction.getType() );
        dto.setLocation( transaction.getLocation() );
        dto.setTimestamp( transaction.getTimestamp() );

        Response response = new Response.Builder()
                .account( account )
                .currency( currency )
                .data( dto )
                .success()
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    //endregion

    //region DELETE /account/users/{index}

    @RequestMapping( value = "/{index}" ,method = RequestMethod.DELETE )
    public ResponseEntity<Response> deleteByIndex( @PathVariable( "index" ) int index, @Valid @RequestBody UserAuthenticationDTO dto, BindingResult result ){

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
