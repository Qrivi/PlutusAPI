package be.plutus.api.endpoint;

import be.plutus.api.request.AccountCreateDTO;
import be.plutus.api.request.AccountRemoveDTO;
import be.plutus.api.request.AccountUpdateDTO;
import be.plutus.api.response.AccountDTO;
import be.plutus.api.response.Response;
import be.plutus.api.response.meta.Meta;
import be.plutus.core.model.account.Account;
import be.plutus.core.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

    @Autowired
    EndpointUtils endpointUtils;

    @RequestMapping( method = RequestMethod.GET )
    public ResponseEntity<Response> get( Authentication authentication ){

        Account account = accountService.getAccount( (Integer)authentication.getPrincipal() );

        AccountDTO dto = new AccountDTO();
        dto.setEmail( account.getEmail() );
        dto.setCurrency( account.getDefaultCurrency() );
        dto.setCreated( account.getCreationDate() );

        return new ResponseEntity<>( new Response<>( Meta.success(), dto ), HttpStatus.OK );
    }

    @RequestMapping( method = RequestMethod.POST )
    public ResponseEntity<Response> post( @Valid @RequestBody AccountCreateDTO dto, BindingResult result ){

        if( result.hasErrors() )
            return endpointUtils.createErrorResponse( result );

        accountService.createAccount( dto.getEmail(), dto.getPassword(), dto.getDefaultCurrency() );

        return new ResponseEntity<>( new Response<>( Meta.success() ), HttpStatus.CREATED );
    }

    @RequestMapping( method = RequestMethod.PUT )
    public ResponseEntity<Response> put( @Valid @RequestBody AccountUpdateDTO dto, BindingResult result, Authentication authentication ){

        if( result.hasErrors() )
            return endpointUtils.createErrorResponse( result );

        accountService.updateAccount( (Integer)authentication.getPrincipal(), dto.getNewEmail(), dto.getNewPassword(), dto.getNewDefaultCurrency() );

        return new ResponseEntity<>( new Response<>( Meta.success() ), HttpStatus.OK );
    }

    @RequestMapping( method = RequestMethod.DELETE )
    public ResponseEntity<Response> delete( @Valid @RequestBody AccountRemoveDTO dto, BindingResult result, Authentication authentication ){

        if( result.hasErrors() )
            return endpointUtils.createErrorResponse( result );

        accountService.removeAccount( (Integer)authentication.getPrincipal() );

        return new ResponseEntity<>( new Response<>( Meta.success() ), HttpStatus.OK );
    }
}
