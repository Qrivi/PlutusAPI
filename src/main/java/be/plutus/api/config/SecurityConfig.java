package be.plutus.api.config;

import be.plutus.api.security.filter.TokenAuthenticationFilter;
import be.plutus.api.utils.MessageService;
import be.plutus.core.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MessageService messageService;

    @Override
    public void configure( WebSecurity web ) throws Exception{
        web
                .ignoring()
                .antMatchers( HttpMethod.POST, "/auth" )
                .antMatchers( HttpMethod.POST, "/account" );
    }

    @Override
    protected void configure( HttpSecurity http ) throws Exception{
        http
                .authorizeRequests()
                .anyRequest().fullyAuthenticated()
                .and()
                .addFilterBefore( new TokenAuthenticationFilter( tokenService, objectMapper, messageService ), BasicAuthenticationFilter.class )
                .sessionManagement()
                .sessionCreationPolicy( SessionCreationPolicy.STATELESS )
                .and()
                .httpBasic().disable()
                .csrf().disable()
                .anonymous().disable();
    }
}