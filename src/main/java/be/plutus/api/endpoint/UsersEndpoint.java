package be.plutus.api.endpoint;

import be.plutus.api.response.Response;
import be.plutus.api.response.UserDTO;
import be.plutus.api.security.context.SecurityContext;
import be.plutus.core.model.account.Account;
import be.plutus.core.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(
        path = "/account/users",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE )
public class UsersEndpoint{

    @Autowired
    AccountService accountService;

    @RequestMapping( method = RequestMethod.GET )
    public ResponseEntity<Response> get(){

        Account account = accountService.getAccount( SecurityContext.getAccount().getId() );

        final int[] index = {0};
        List<UserDTO> users = account.getUsers()
                .stream()
                .map( user -> {
                    UserDTO dto = new UserDTO();
                    dto.setIndex( index[0]++ );
                    dto.setUsername( user.getUsername() );
                    dto.setFirstName( user.getFirstName() );
                    dto.setLastName( user.getLastName() );
                    dto.setInstitution( user.getInstitution() );
                    dto.setUpdated( user.getFetchDate() );
                    return dto;
                } ).collect( Collectors.toList() );


        Response response = new Response.Builder()
                .data( users )
                .success()
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }
}
