package be.plutus.api.endpoint;

import be.plutus.api.config.Config;
import be.plutus.api.endpoint.utils.EndpointUtils;
import be.plutus.api.dto.request.UserAuthenticationDTO;
import be.plutus.api.dto.request.UserUCLLCreateDTO;
import be.plutus.api.dto.request.UserUpdateDTO;
import be.plutus.api.response.AccountMeta;
import be.plutus.api.response.Meta;
import be.plutus.api.response.Response;
import be.plutus.api.dto.response.TransactionDTO;
import be.plutus.api.dto.response.UserDTO;
import be.plutus.api.response.UserMeta;
import be.plutus.api.security.context.SecurityContext;
import be.plutus.api.utils.Converter;
import be.plutus.core.model.account.Account;
import be.plutus.core.model.account.Credit;
import be.plutus.core.model.account.User;
import be.plutus.core.model.currency.Currency;
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
                .map( user -> Converter.convert( user, index[0]++ ) )
                .collect( Collectors.toList() );

        Meta meta = new AccountMeta.Builder()
                .account( account.getEmail() )
                .currency( account.getDefaultCurrency() )
                .success()
                .build();

        Response response = new Response.Builder()
                .meta( meta )
                .data( users )
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
                .meta( Meta.success() )
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    //endregion

    //region POST /account/users?institution=ucll

    @RequestMapping( params = {"institution=ucll"}, method = RequestMethod.POST )
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
                .meta( Meta.created() )
                .build();

        return new ResponseEntity<>( response, HttpStatus.CREATED );
    }

    //endregion

    //region GET /account/users/{index}

    @RequestMapping( value = "/{index}", method = RequestMethod.GET )
    public ResponseEntity<Response> getByIndex( @PathVariable( "index" ) int index ){

        Account account = accountService.getAccount( SecurityContext.getAccount().getId() );
        User user = ( index < 0 || index > account.getUsers().size() - 1 ) ? null : account.getUsers().get( index );

        if( user == null )
            return new ResponseEntity<>( new Response.Builder().meta( Meta.notFound() ).build(), HttpStatus.NOT_FOUND );

        Meta meta = new AccountMeta.Builder()
                .account( account.getEmail() )
                .currency( account.getDefaultCurrency() )
                .success()
                .build();

        Response response = new Response.Builder()
                .meta( meta )
                .data( Converter.convert( user, index ) )
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    //endregion

    //region PUT /account/users/{index}

    @RequestMapping( value = "/{index}", method = RequestMethod.PUT )
    public ResponseEntity<Response> putByIndex( @PathVariable( "index" ) int index, @Valid @RequestBody UserUpdateDTO dto, BindingResult result ){

        if( result.hasErrors() )
            return EndpointUtils.createErrorResponse( result );

        Account account = accountService.getAccount( SecurityContext.getAccount().getId() );
        User user = ( index < 0 || index > account.getUsers().size() - 1 ) ? null : account.getUsers().get( index );

        if( user == null )
            return new ResponseEntity<>( new Response.Builder().meta( Meta.notFound() ).build(), HttpStatus.NOT_FOUND );

        accountService.updateUser( user.getId(), dto.getNewPassword() );

        Response response = new Response.Builder()
                .meta( Meta.success() )
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    //endregion

    //region POST /account/users/{index}/reset

    @RequestMapping( value = "/{index}/reset", method = RequestMethod.POST )
    public ResponseEntity<Response> resetByIndex( @PathVariable( "index" ) int index, @Valid @RequestBody UserAuthenticationDTO dto, BindingResult result ){

        if( result.hasErrors() )
            return EndpointUtils.createErrorResponse( result );

        Account account = accountService.getAccount( SecurityContext.getAccount().getId() );
        User user = ( index < 0 || index > account.getUsers().size() - 1 ) ? null : account.getUsers().get( index );

        if( user == null )
            return new ResponseEntity<>( new Response.Builder().meta( Meta.notFound() ).build(), HttpStatus.NOT_FOUND );

        accountService.resetTransactionsFromUser( user.getId() );

        Response response = new Response.Builder()
                .meta( Meta.success() )
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    //endregion

    //region GET /account/users/{index}/credit

    @RequestMapping( value = "/{index}/credit", method = RequestMethod.GET )
    public ResponseEntity<Response> getCreditByIndex( @PathVariable( "index" ) int index, @RequestParam( name = "currency", required = false ) String currencyParam ){

        Account account = accountService.getAccount( SecurityContext.getAccount().getId() );
        User user = ( index < 0 || index > account.getUsers().size() - 1 ) ? null : account.getUsers().get( index );

        if( user == null )
            return new ResponseEntity<>( new Response.Builder().meta( Meta.notFound() ).build(), HttpStatus.NOT_FOUND );

        Credit credit = user.getCredit();
        Currency currency = Optional.ofNullable( Currency.getFromAbbreviation( currencyParam ) )
                .orElse( account.getDefaultCurrency() );

        Meta meta = new UserMeta.Builder()
                .user( user.getUsername() )
                .updated( user.getFetchDate() )
                .account( account.getEmail() )
                .currency( currency )
                .success()
                .build();

        Response response = new Response.Builder()
                .meta( meta )
                .data( Converter.convert( credit, currency ) )
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    //endregion

    //region GET /account/users/{index}/transactions

    @RequestMapping( value = "/{index}/transactions", method = RequestMethod.GET )
    public ResponseEntity<Response> getTransactionsByIndex( @PathVariable( "index" ) int index,
                                                            @RequestParam( name = "currency", required = false ) String currencyParam,
                                                            @RequestParam( name = "limit", required = false ) Integer limitParam,
                                                            @RequestParam( name = "offset", required = false ) Integer offsetParam,
                                                            @RequestParam( name = "type", required = false ) String typeParam ){

        Account account = accountService.getAccount( SecurityContext.getAccount().getId() );
        User user = ( index < 0 || index > account.getUsers().size() - 1 ) ? null : account.getUsers().get( index );

        if( user == null )
            return new ResponseEntity<>( new Response.Builder().meta( Meta.notFound() ).build(), HttpStatus.NOT_FOUND );

        Currency currency = Optional.ofNullable( Currency.getFromAbbreviation( currencyParam ) )
                .orElse( account.getDefaultCurrency() );

        int limit = Optional.ofNullable( limitParam )
                .orElse( Config.DEFAULT_TRANSACTION_LIMIT );

        int offset = Optional.ofNullable( offsetParam )
                .orElse( Config.DEFAULT_TRANSACTION_OFFSET );

        TransactionType type = TransactionType.getFromAbbreviation( typeParam );

        List<Transaction> transactions;

        if( type == null )
            transactions = transactionService.getTransactionByUser( user.getId(), limit, offset );
        else
            transactions = transactionService.getTransactionByUserAndType( user.getId(), type, limit, offset );

        List<TransactionDTO> transactionDTOs = transactions
                .stream()
                .map( transaction -> Converter.convert( transaction, currency ) )
                .collect( Collectors.toList() );

        Meta meta = new UserMeta.Builder()
                .user( user.getUsername() )
                .updated( user.getFetchDate() )
                .account( account.getEmail() )
                .currency( currency )
                .success()
                .build();

        Response response = new Response.Builder()
                .meta( meta )
                .data( transactionDTOs )
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    //endregion

    //region GET /account/users/{index}/transactions/{id}

    @RequestMapping( value = "/{index}/transactions/{id}", method = RequestMethod.GET )
    public ResponseEntity<Response> getTransactionsByIndex( @PathVariable( "index" ) int index,
                                                            @PathVariable( "id" ) int id,
                                                            @RequestParam( name = "currency", required = false ) String currencyParam ){

        Account account = accountService.getAccount( SecurityContext.getAccount().getId() );
        User user = ( index < 0 || index > account.getUsers().size() - 1 ) ? null : account.getUsers().get( index );

        if( user == null )
            return new ResponseEntity<>( new Response.Builder().meta( Meta.notFound() ).build(), HttpStatus.NOT_FOUND );

        Currency currency = Optional.ofNullable( Currency.getFromAbbreviation( currencyParam ) )
                .orElse( account.getDefaultCurrency() );

        Transaction transaction = transactionService.getTransaction( id );

        if( !Objects.equals( transaction.getUser().getId(), user.getId() ) )
            return new ResponseEntity<>( new Response.Builder().meta( Meta.notFound() ).build(), HttpStatus.NOT_FOUND );

        Meta meta = new UserMeta.Builder()
                .user( user.getUsername() )
                .updated( user.getFetchDate() )
                .account( account.getEmail() )
                .currency( currency )
                .success()
                .build();

        Response response = new Response.Builder()
                .meta( meta )
                .data( Converter.convert( transaction, currency ) )
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    //endregion

    //region DELETE /account/users/{index}

    @RequestMapping( value = "/{index}", method = RequestMethod.DELETE )
    public ResponseEntity<Response> deleteByIndex( @PathVariable( "index" ) int index, @Valid @RequestBody UserAuthenticationDTO dto, BindingResult result ){

        if( result.hasErrors() )
            return EndpointUtils.createErrorResponse( result );

        accountService.removeUserFromAccount( SecurityContext.getAccount().getId(), index );

        Response response = new Response.Builder()
                .meta( Meta.success() )
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    //endregion
}
