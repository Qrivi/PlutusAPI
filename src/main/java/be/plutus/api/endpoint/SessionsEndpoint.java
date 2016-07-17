package be.plutus.api.endpoint;

import be.plutus.api.security.context.SecurityContext;
import be.plutus.api.response.RequestDTO;
import be.plutus.api.response.Response;
import be.plutus.api.response.SessionDTO;
import be.plutus.core.model.token.Token;
import be.plutus.core.service.AccountService;
import be.plutus.core.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(
        path = "/account/sessions",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE )
public class SessionsEndpoint{

    @Autowired
    TokenService tokenService;

    @Autowired
    AccountService accountService;

    //region GET /account/sessions

    @RequestMapping( method = RequestMethod.GET )
    public ResponseEntity<Response> get(){

        Response response = new Response.Builder()
                .data( getSessions() )
                .success()
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    //endregion

    //region GET /account/sessions/{index}

    @RequestMapping( value = "/{index}", method = RequestMethod.GET )
    public ResponseEntity<Response> getByIndex( @PathVariable( value = "index" ) Integer index ){

        List<SessionDTO> sessions = getSessions();

        if( index < 0 || index > sessions.size() - 1 )
            return new ResponseEntity<>( new Response.Builder().notFound().build(), HttpStatus.NOT_FOUND );

        Response response = new Response.Builder()
                .data( sessions.get( index ) )
                .success()
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    //endregion

    //region DELETE /account/sessions/{index}

    @RequestMapping( value = "/{index}", method = RequestMethod.DELETE )
    public ResponseEntity<Response> delete( @PathVariable( value = "index" ) Integer index ){

        List<Token> tokens = tokenService.getTokensFromAccount( SecurityContext.getAccount().getId() );

        if( index < 0 || index > tokens.size() - 1 )
            return new ResponseEntity<>( new Response.Builder().notFound().build(), HttpStatus.NOT_FOUND );

        tokenService.deactivateToken( tokens.get( index ).getId() );

        Response response = new Response.Builder()
                .success()
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    //endregion

    //region GET /account/sessions/{index}/requests

    @RequestMapping( value = "/{index}/requests", method = RequestMethod.GET )
    public ResponseEntity<Response> getRequests( @PathVariable( value = "index" ) Integer index ){

        List<Token> tokens = tokenService.getTokensFromAccount( SecurityContext.getAccount().getId() );

        if( index < 0 || index > tokens.size() - 1 )
            return new ResponseEntity<>( new Response.Builder().notFound().build(), HttpStatus.NOT_FOUND );

        List<RequestDTO> requests =  getRequestsFromToken( tokens.get( index ).getId() );

        Response response = new Response.Builder()
                .data( requests )
                .success()
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    //endregion

    private List<SessionDTO> getSessions(){
        final int[] index = {0};
        return tokenService.getTokensFromAccount( SecurityContext.getAccount().getId() )
                .stream().map( token -> {
                    SessionDTO dto = new SessionDTO();
                    dto.setIndex( index[0]++ );
                    dto.setApplication( token.getApplicationName() );
                    dto.setDevice( token.getDeviceName() );
                    dto.setIp( token.getRequestIp() );
                    dto.setCreated( token.getCreationDate() );
                    dto.setExpires( token.getExpiryDate() );
                    return dto;
                } ).collect( Collectors.toList() );
    }

    private List<RequestDTO> getRequestsFromToken( int id ){
        return tokenService.getRequestsFromToken( id )
                .stream().map( request -> {
                    RequestDTO dto = new RequestDTO();
                    dto.setMethod( request.getMethod() );
                    dto.setEndpoint( request.getEndpoint() );
                    dto.setIp( request.getIp() );
                    dto.setTimestamp( request.getTimestamp() );
                    return dto;
                } ).collect( Collectors.toList() );
    }
}
