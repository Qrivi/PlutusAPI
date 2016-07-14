package be.plutus.api.endpoint;

import be.plutus.api.request.AccountCreateDTO;
import be.plutus.api.request.AccountRemoveDTO;
import be.plutus.api.request.AccountUpdateDTO;
import be.plutus.api.response.dto.AccountDTO;
import be.plutus.api.response.Response;
import be.plutus.api.security.SecurityContext;
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

        Account account = accountService.getAccount( SecurityContext.getAccount().getId() );

        AccountDTO dto = new AccountDTO();
        dto.setEmail( account.getEmail() );
        dto.setCurrency( account.getDefaultCurrency() );
        dto.setCreated( account.getCreationDate() );

        Response response = new Response.Builder()
                .data( dto )
                .success()
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    @RequestMapping( method = RequestMethod.POST )
    public ResponseEntity<Response> post( @Valid @RequestBody AccountCreateDTO dto, BindingResult result ){

        if( result.hasErrors() )
            return EndpointUtils.createErrorResponse( result );

        accountService.createAccount( dto.getEmail(), dto.getPassword(), dto.getDefaultCurrency() );

        Response response = new Response.Builder()
                .created()
                .build();

        return new ResponseEntity<>( response, HttpStatus.CREATED );
    }

    @RequestMapping( method = RequestMethod.PUT )
    public ResponseEntity<Response> put( @Valid @RequestBody AccountUpdateDTO dto, BindingResult result ){

        if( result.hasErrors() )
            return EndpointUtils.createErrorResponse( result );

        accountService.updateAccount( SecurityContext.getAccount().getId(), dto.getNewEmail(), dto.getNewPassword(), dto.getNewDefaultCurrency() );

        Response response = new Response.Builder()
                .success()
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    @RequestMapping( method = RequestMethod.DELETE )
    public ResponseEntity<Response> delete( @Valid @RequestBody AccountRemoveDTO dto, BindingResult result ){

        if( result.hasErrors() )
            return EndpointUtils.createErrorResponse( result );

        accountService.removeAccount( SecurityContext.getAccount().getId() );

        Response response = new Response.Builder()
                .success()
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }
}
