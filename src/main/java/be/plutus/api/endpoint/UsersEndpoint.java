package be.plutus.api.endpoint;

import be.plutus.api.config.Config;
import be.plutus.api.util.Converter;
import be.plutus.api.request.UserAuthenticationDTO;
import be.plutus.api.request.UserUCLLCreateDTO;
import be.plutus.api.request.UserUpdateDTO;
import be.plutus.api.response.*;
import be.plutus.api.security.context.SecurityContext;
import be.plutus.core.model.account.Account;
import be.plutus.core.model.account.Credit;
import be.plutus.core.model.account.User;
import be.plutus.core.model.currency.Currency;
import be.plutus.core.model.currency.CurrencyConverter;
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
import java.util.Objects;
import java.util.Optional;
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
                .map( user -> Converter.convert( user, index[0]++ ))
                .collect( Collectors.toList() );

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

        Response response = new Response.Builder()
                .account( account )
                .data( Converter.convert( user, index ) )
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

        Currency currency = Currency.getFromAbbreviation( currencyName );

        if (currency == null)
            currency = account.getDefaultCurrency();

        Response response = new Response.Builder()
                .account( account )
                .user( user )
                .currency( currency )
                .data( Converter.convert( credit, currency ) )
                .success()
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    //endregion

    //region GET /account/users/{index}/transactions

    @RequestMapping( value = "/{index}/transactions" ,method = RequestMethod.GET )
    public ResponseEntity<Response> getTransactionsByIndex( @PathVariable( "index" ) int index,
                                                            @RequestParam( name = "currency", required = false ) String currencyName,
                                                            @RequestParam( name = "limit", required = false ) Integer limit,
                                                            @RequestParam( name = "offset", required = false ) Integer offset,
                                                            @RequestParam( name = "type", required = false ) String typeName ){

        Account account = accountService.getAccount( SecurityContext.getAccount().getId() );
        List<User> users = account.getUsers();

        if( index < 0 || index > users.size() - 1 )
            return new ResponseEntity<>( new Response.Builder().notFound().build(), HttpStatus.NOT_FOUND );

        User user = users.get( index );

        Currency currency = Optional
                .ofNullable( Currency.getFromAbbreviation( currencyName ) )
                .orElse( account.getDefaultCurrency() );
        limit = Optional
                .ofNullable( limit )
                .orElse( Config.DEFAULT_TRANSACTION_LIMIT );
        offset = Optional
                .ofNullable( offset )
                .orElse( Config.DEFAULT_TRANSACTION_OFFSET );

        TransactionType type = TransactionType.getFromAbbreviation( typeName );

        List<Transaction> transactions;

        if (type == null)
            transactions = transactionService.getTransactionByUser( user.getId(), limit, offset );
        else
            transactions = transactionService.getTransactionByUserAndType( user.getId(), type, limit, offset );

        List<TransactionDTO> transactionDTOs = transactions
                .stream()
                .map( transaction -> Converter.convert(transaction, currency) )
                .collect( Collectors.toList() );

        Response response = new Response.Builder()
                .account( account )
                .currency( currency )
                .user( user )
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

        Currency currency = Currency.getFromAbbreviation( currencyName );
        currency = (currency == null) ? account.getDefaultCurrency() : currency;

        Transaction transaction = transactionService.getTransaction( id );

        if( !Objects.equals( transaction.getUser().getId(), user.getId() ) )
            return new ResponseEntity<>( new Response.Builder().notFound().build(), HttpStatus.NOT_FOUND );

        Response response = new Response.Builder()
                .account( account )
                .currency( currency )
                .user( user )
                .data( Converter.convert( transaction, currency ) )
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
