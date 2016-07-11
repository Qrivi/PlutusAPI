package be.plutus.api.endpoint;

import be.plutus.api.request.PreferenceCreateDTO;
import be.plutus.api.request.PreferenceValueCreateDTO;
import be.plutus.api.response.Response;
import be.plutus.api.response.meta.Meta;
import be.plutus.core.model.account.Account;
import be.plutus.core.model.preferences.Preferences;
import be.plutus.core.service.PreferencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<Response> get( Authentication authentication ){

        Preferences preferences = preferencesService.getPreferenceFromAccount( (Integer)authentication.getPrincipal() );
        return new ResponseEntity<>( new Response<>( Meta.success(), preferences.toMap() ), HttpStatus.OK );
    }

    @RequestMapping( method = RequestMethod.POST )
    public ResponseEntity<Response> post( @RequestBody PreferenceCreateDTO dto, Authentication authentication){

        Preferences preferences = preferencesService.getPreferenceFromAccount( (Integer)authentication.getPrincipal() );

        preferencesService.addPreference( preferences.getId(), dto.getKey(), dto.getValue() );
        return new ResponseEntity<>( new Response<>( Meta.success() ), HttpStatus.OK );
    }

    @RequestMapping( value = "/{key}", method = RequestMethod.GET )
    public ResponseEntity<Response> getByKey( @PathVariable( "key" ) String key, Authentication authentication){

        Preferences preferences = preferencesService.getPreferenceFromAccount( (Integer)authentication.getPrincipal() );

        Map<String, String> preference = new HashMap<>();
        preference.put( key, preferences.get(key) );

        return new ResponseEntity<>( new Response<>( Meta.success(), preference ), HttpStatus.OK );
    }

    @RequestMapping( value = "/{key}", method = RequestMethod.PUT )
    public ResponseEntity<Response> putKey( @PathVariable( "key" ) String key, @RequestBody PreferenceValueCreateDTO dto, Authentication authentication){

        Preferences preferences = preferencesService.getPreferenceFromAccount( (Integer)authentication.getPrincipal() );

        preferencesService.addPreference( preferences.getId(), key, dto.getValue() );
        return new ResponseEntity<>( new Response<>( Meta.success() ), HttpStatus.OK );
    }
}
