package be.plutus.api.endpoint;

import be.plutus.api.response.Response;
import be.plutus.api.response.meta.Meta;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.stream.Collectors;

@Component
public class EndpointUtils{

    public ResponseEntity<Response> createErrorResponse( BindingResult result ){
        Response<Meta, Object> response = new Response<>();
        response.setMeta( Meta.badRequest() );
        response.setErrors( result.getAllErrors()
                .stream()
                .map( DefaultMessageSourceResolvable::getDefaultMessage )
                .collect( Collectors.toList() )
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
