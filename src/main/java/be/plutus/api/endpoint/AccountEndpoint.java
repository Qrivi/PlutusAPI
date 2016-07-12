package be.plutus.api.endpoint;

import be.plutus.api.request.AccountCreateDTO;
import be.plutus.api.request.AccountRemoveDTO;
import be.plutus.api.request.AccountUpdateDTO;
import be.plutus.api.response.AccountDTO;
import be.plutus.api.response.Response;
import be.plutus.api.security.Auth;
import be.plutus.core.model.account.Account;
import be.plutus.core.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(
        path = "/account",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE )
public class AccountEndpoint{

    @Autowired
    AccountService accountService;

    @RequestMapping( method = RequestMethod.GET )
    public ResponseEntity<Response> get(){

        Account account = accountService.getAccount( Auth.current().getId() );

        AccountDTO dto = new AccountDTO();
        dto.setEmail( account.getEmail() );
        dto.setCurrency( account.getDefaultCurrency() );
        dto.setCreated( account.getCreationDate() );

        return new ResponseEntity<>( new Response.Builder()
                .data( dto )
                .success()
                .build(), HttpStatus.OK );
    }

    @RequestMapping( method = RequestMethod.POST )
    public ResponseEntity<Response> post( @Valid @RequestBody AccountCreateDTO dto, BindingResult result ){

        if( result.hasErrors() )
            return EndpointUtils.createErrorResponse( result );

        accountService.createAccount( dto.getEmail(), dto.getPassword(), dto.getDefaultCurrency() );

        return new ResponseEntity<>( new Response.Builder()
                .success()
                .build(), HttpStatus.CREATED );
    }

    @RequestMapping( method = RequestMethod.PUT )
    public ResponseEntity<Response> put( @Valid @RequestBody AccountUpdateDTO dto, BindingResult result ){

        if( result.hasErrors() )
            return EndpointUtils.createErrorResponse( result );

        accountService.updateAccount( Auth.current().getId(), dto.getNewEmail(), dto.getNewPassword(), dto.getNewDefaultCurrency() );

        return new ResponseEntity<>( new Response.Builder()
                .success()
                .build(), HttpStatus.OK );
    }

    @RequestMapping( method = RequestMethod.DELETE )
    public ResponseEntity<Response> delete( @Valid @RequestBody AccountRemoveDTO dto, BindingResult result ){

        if( result.hasErrors() )
            return EndpointUtils.createErrorResponse( result );

        accountService.removeAccount( Auth.current().getId() );

        return new ResponseEntity<>( new Response.Builder()
                .success()
                .build(), HttpStatus.OK );
    }
}
