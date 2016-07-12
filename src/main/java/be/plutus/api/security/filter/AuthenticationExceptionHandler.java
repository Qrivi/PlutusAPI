package be.plutus.api.security.filter;

import be.plutus.api.response.Response;
import be.plutus.api.security.exception.AuthenticationException;
import be.plutus.api.util.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationExceptionHandler{

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MessageService messageService;

    public void handle( HttpServletResponse res, AuthenticationException e ) throws IOException{
        Response.Builder response = new Response.Builder();

        response.unauthorized();
        response.errors( messageService.get( e.getClass().getName() ) );

        res.setContentType( "application/json" );
        res.setStatus( HttpServletResponse.SC_UNAUTHORIZED );

        objectMapper.writeValue( res.getOutputStream(), response.build() );
    }
}
