package be.plutus.api.endpoint;

import be.plutus.api.dto.response.InstitutionDTO;
import be.plutus.api.response.Meta;
import be.plutus.api.response.Response;
import be.plutus.api.utils.Converter;
import be.plutus.core.service.LocationService;
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
        path = "/institutions",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE )
public class InstitutionsEndpoint{

    @Autowired
    LocationService locationService;

    //region GET /institutions

    @RequestMapping( method = RequestMethod.GET )
    public ResponseEntity<Response> get(){

        List<InstitutionDTO> institutions = locationService.getAllInstitutions()
                .stream()
                .map( Converter::convert )
                .collect( Collectors.toList() );

        Response response = new Response.Builder()
                .meta( Meta.success() )
                .data( institutions )
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    //endregion
}
