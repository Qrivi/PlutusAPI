package be.plutus.api.endpoint;

import be.plutus.api.request.AuthenticationDTO;
import be.plutus.api.response.Response;
import be.plutus.api.response.TokenDTO;
import be.plutus.api.response.meta.Meta;
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
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    EndpointUtils endpointUtils;

    @RequestMapping( method = RequestMethod.POST )
    public ResponseEntity<Response> post( @Valid @RequestBody AuthenticationDTO dto, BindingResult result, HttpServletRequest request ){

        if( result.hasErrors() )
            return endpointUtils.createErrorResponse( result );

        Account account = accountService.getAccount( dto.getEmail() );
        Response<Meta, Object> response = new Response<>();

        if( account == null ){
            response.setMeta( Meta.badRequest() );
            response.setErrors( messageService.get( "NotValid.AuthEndpoint.email" ) );
            return new ResponseEntity<>( response, HttpStatus.BAD_REQUEST );
        }

        if( !account.isPasswordValid( dto.getPassword() ) ){
            response.setMeta( Meta.badRequest() );
            response.setErrors( messageService.get( "NotValid.AuthEndpoint.password" ) );
            return new ResponseEntity<>( response, HttpStatus.BAD_REQUEST );
        }

        if( account.getStatus() != AccountStatus.ACTIVE ){
            response.setMeta( Meta.forbidden() );
            response.setErrors( account.getStatus().getStatus() );
            return new ResponseEntity<>( response, HttpStatus.FORBIDDEN );
        }

        Token token = tokenService.createToken( account, dto.getApplication(), dto.getDevice(), request.getRemoteAddr());

        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setToken( token.getToken() );
        tokenDTO.setApplication( token.getApplicationName() );
        tokenDTO.setDevice( token.getDeviceName() );
        tokenDTO.setExpires( token.getExpiryDate() );

        response.setMeta( Meta.success() );
        response.setData( tokenDTO );

        return new ResponseEntity<>( response, HttpStatus.OK );

    }
}
