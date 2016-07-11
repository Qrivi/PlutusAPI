package be.plutus.api.security;

import be.plutus.api.response.Response;
import be.plutus.api.response.meta.Meta;
import be.plutus.api.security.exception.AuthenticationException;
import be.plutus.api.util.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationExceptionHandler implements ExceptionHandler<AuthenticationException>{

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MessageService messageService;

    @Override
    public void handle( HttpServletRequest req, HttpServletResponse res, AuthenticationException e ) throws IOException{
        Response.Builder response = new Response.Builder();

        response.unauthorized();
        response.errors( messageService.get( e.getClass().getName() ) );

        res.setContentType( "application/json" );
        res.setStatus( HttpServletResponse.SC_UNAUTHORIZED );

        objectMapper.writeValue( res.getOutputStream(), response.build() );
    }
}
