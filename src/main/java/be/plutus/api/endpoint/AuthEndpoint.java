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
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping(
        path = "/auth",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE )
public class AuthEndpoint{

    @Autowired
    AccountService accountService;

    @Autowired
    TokenService tokenService;

    @Autowired
    MessageService messageService;

    @RequestMapping( method = RequestMethod.POST )
    public ResponseEntity<Response<Meta, TokenDTO>> post(
            @Valid @RequestBody AuthenticationDTO dto,
            BindingResult bindingResult,
            HttpServletRequest request ){
        Response<Meta, TokenDTO> response = new Response<>();

        //TODO kan dit afgezonderd worden in hogere klasse om niet voor elk endpoint herhaald te moeten worden?
        if( bindingResult.hasErrors() ){
            response.setMeta( Meta.badRequest() );
            response.setErrors( bindingResult.getAllErrors()
                    .stream()
                    .map( DefaultMessageSourceResolvable::getDefaultMessage )
                    .collect( Collectors.toList() )
            );
            return new ResponseEntity<>( response, HttpStatus.BAD_REQUEST );
        }

        Account account = accountService.getAccount( dto.getEmail() );

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
