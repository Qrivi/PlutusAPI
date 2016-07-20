package be.plutus.api.endpoint;

import be.plutus.api.converter.Converter;
import be.plutus.api.request.AuthenticationDTO;
import be.plutus.api.response.Response;
import be.plutus.api.response.TokenDTO;
import be.plutus.api.util.MessageService;
import be.plutus.core.model.account.Account;
import be.plutus.core.model.account.AccountStatus;
import be.plutus.core.model.token.Token;
import be.plutus.core.service.AccountService;
import be.plutus.core.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(
        path = "/auth",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE )
public class AuthEndpoint{

    @Autowired
    TokenService tokenService;

    @Autowired
    AccountService accountService;

    @Autowired
    MessageService messageService;

    //region POST /auth

    @RequestMapping( method = RequestMethod.POST )
    public ResponseEntity<Response> post( @Valid @RequestBody AuthenticationDTO dto, BindingResult result, HttpServletRequest request ){

        if( result.hasErrors() )
            return EndpointUtils.createErrorResponse( result );

        Account account = accountService.getAccount( dto.getEmail() );

        Response.Builder response = new Response.Builder();

        if( account == null ){
            response.errors( messageService.get( "NotValid.AuthEndpoint.email" ) );
            response.badRequest();
            return new ResponseEntity<>( response.build(), HttpStatus.BAD_REQUEST );
        }

        if( !account.isPasswordValid( dto.getPassword() ) ){
            response.errors( messageService.get( "NotValid.AuthEndpoint.password" ) );
            response.badRequest();
            return new ResponseEntity<>( response.build(), HttpStatus.BAD_REQUEST );
        }

        if( account.getStatus() != AccountStatus.ACTIVE ){
            response.errors( account.getStatus().getStatus() );
            response.forbidden();
            return new ResponseEntity<>( response.build(), HttpStatus.FORBIDDEN );
        }

        Token token = tokenService.createToken( account, dto.getApplication(), dto.getDevice(), request.getRemoteAddr() );

        response.data( Converter.convert( token ) );
        response.success();

        return new ResponseEntity<>( response.build(), HttpStatus.OK );
    }

    //endregion
}
