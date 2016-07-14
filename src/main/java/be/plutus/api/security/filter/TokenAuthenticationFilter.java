package be.plutus.api.security.filter;

import be.plutus.api.security.*;
import be.plutus.core.model.account.Account;
import be.plutus.core.model.account.AccountStatus;
import be.plutus.core.model.token.Token;
import be.plutus.core.service.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;

public class TokenAuthenticationFilter extends GenericFilterBean{

    private static String PARAMETER_SECURITY_TOKEN = "token";
    private static String HEADER_SECURITY_TOKEN = "X-SecurityContext-Token";

    private TokenService tokenService;
    private AuthenticationExceptionHandler authenticationExceptionHandler;

    public TokenAuthenticationFilter( TokenService tokenService, AuthenticationExceptionHandler authenticationExceptionHandler ){
        this.tokenService = tokenService;
        this.authenticationExceptionHandler = authenticationExceptionHandler;
    }

    @Override
    public void doFilter( ServletRequest req, ServletResponse res, FilterChain chain ) throws IOException, ServletException{
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)res;
        try{
            String tokenHeader = request.getHeader( HEADER_SECURITY_TOKEN );
            String tokenParameter = request.getParameter( PARAMETER_SECURITY_TOKEN );

            if( SecurityContextHolder.getContext().getAuthentication() == null ){

                Token token = null;

                if( tokenHeader == null && tokenParameter == null )
                    throw new TokenNotFoundException();

                if( tokenHeader != null )
                    token = tokenService.getToken( tokenHeader );

                if( tokenParameter != null )
                    token = tokenService.getToken( tokenParameter );

                if( isValid( token ) ){
                    tokenService.extendToken( token );
                    tokenService.createRequest( request.getServletPath(), request.getMethod(), request.getRemoteAddr(), token );

                    Account account = token.getAccount();

                    if( isValid( account ) ){
                        account.clearPassword();
                        SecurityContextHolder
                                .getContext()
                                .setAuthentication(
                                        new UsernamePasswordAuthenticationToken(
                                                account,
                                                null,
                                                Collections.singletonList( ( () -> "ROLE_BASIC" ) )
                                        )
                                );
                    }
                }
            }

            chain.doFilter( request, response );
        }catch( AuthenticationException e ){
            authenticationExceptionHandler.handle( response, e );
        }
    }

    private boolean isValid( Token token ){
        if( token == null )
            throw new TokenInvalidException();
        if( !token.isActive() )
            throw new TokenNotActiveException();
        if( token.getExpiryDate().getTime() < new Date().getTime() )
            throw new TokenExpiredException();
        return true;
    }

    private boolean isValid( Account account ){
        if( account == null )
            throw new AccountNotFoundException();
        if( account.getStatus() != AccountStatus.ACTIVE )
            throw new AccountNotActiveException();
        return true;
    }
}
