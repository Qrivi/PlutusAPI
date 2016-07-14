package be.plutus.api.endpoint;

import be.plutus.api.security.SecurityContext;
import be.plutus.api.request.PreferenceCreateDTO;
import be.plutus.api.request.PreferenceValueCreateDTO;
import be.plutus.api.response.Response;
import be.plutus.core.model.preferences.Preferences;
import be.plutus.core.service.PreferencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(
        path = "/account/preferences",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE )
public class PreferencesEndpoint{

    @Autowired
    private PreferencesService preferencesService;

    @RequestMapping( method = RequestMethod.GET )
    public ResponseEntity<Response> get(){

        Preferences preferences = preferencesService.getPreferenceFromAccount( SecurityContext.getAccount().getId() );

        Response response = new Response.Builder()
                .data(  preferences.toMap() )
                .success()
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    @RequestMapping( method = RequestMethod.POST )
    public ResponseEntity<Response> post( @RequestBody PreferenceCreateDTO dto ){

        Preferences preferences = preferencesService.getPreferenceFromAccount( SecurityContext.getAccount().getId() );

        preferencesService.addPreference( preferences.getId(), dto.getKey(), dto.getValue() );

        Response response = new Response.Builder()
                .created()
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    @RequestMapping( value = "/{key}", method = RequestMethod.GET )
    public ResponseEntity<Response> getByKey( @PathVariable( "key" ) String key ){

        Preferences preferences = preferencesService.getPreferenceFromAccount( SecurityContext.getAccount().getId() );

        Map<String, String> preference = new HashMap<>();
        preference.put( key, preferences.get( key ) );

        Response response = new Response.Builder()
                .data( preference )
                .success()
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    @RequestMapping( value = "/{key}", method = RequestMethod.PUT )
    public ResponseEntity<Response> putKey( @PathVariable( "key" ) String key, @RequestBody PreferenceValueCreateDTO dto ){

        Preferences preferences = preferencesService.getPreferenceFromAccount( SecurityContext.getAccount().getId() );

        preferencesService.addPreference( preferences.getId(), key, dto.getValue() );

        Response response = new Response.Builder()
                .success()
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }
}
