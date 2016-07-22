package be.plutus.api.endpoint;

import be.plutus.api.dto.request.PreferenceCreateDTO;
import be.plutus.api.dto.request.PreferenceValueCreateDTO;
import be.plutus.api.response.Response;
import be.plutus.api.security.context.SecurityContext;
import be.plutus.core.model.preferences.Preferences;
import be.plutus.core.service.PreferencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(
        path = "/account/preferences",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE )
public class PreferencesEndpoint{

    @Autowired
    private PreferencesService preferencesService;

    //region GET /account/preferences

    @RequestMapping( method = RequestMethod.GET )
    public ResponseEntity<Response> get(){

        Preferences preferences = preferencesService.getPreferenceFromAccount( SecurityContext.getAccount().getId() );

        Response response = new Response.Builder()
                .data( preferences.toMap() )
                .success()
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    //endregion

    //region POST /account/preferences

    @RequestMapping( method = RequestMethod.POST )
    public ResponseEntity<Response> post( @RequestBody PreferenceCreateDTO dto ){

        Preferences preferences = preferencesService.getPreferenceFromAccount( SecurityContext.getAccount().getId() );

        preferencesService.addPreference( preferences.getId(), dto.getKey(), dto.getValue() );

        Response response = new Response.Builder()
                .created()
                .build();

        return new ResponseEntity<>( response, HttpStatus.CREATED );
    }

    //endregion

    //region GET /account/preferences/{key}

    @RequestMapping( value = "/{key}", method = RequestMethod.GET )
    public ResponseEntity<Response> getByKey( @PathVariable( "key" ) String key ){

        Preferences preferences = preferencesService.getPreferenceFromAccount( SecurityContext.getAccount().getId() );

        Response response = new Response.Builder()
                .data( new HashMap<String, String>(){{
                    put( key, preferences.get( key ) );
                }} )
                .success()
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    //endregion

    //region PUT /account/preferences/{key}

    @RequestMapping( value = "/{key}", method = RequestMethod.PUT )
    public ResponseEntity<Response> putKey( @PathVariable( "key" ) String key, @RequestBody PreferenceValueCreateDTO dto ){

        Preferences preferences = preferencesService.getPreferenceFromAccount( SecurityContext.getAccount().getId() );

        preferencesService.addPreference( preferences.getId(), key, dto.getValue() );

        Response response = new Response.Builder()
                .success()
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    //endregion
}
