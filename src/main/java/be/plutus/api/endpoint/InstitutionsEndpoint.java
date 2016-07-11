package be.plutus.api.endpoint;

import be.plutus.api.response.InstitutionDTO;
import be.plutus.api.response.Response;
import be.plutus.api.response.meta.Meta;
import be.plutus.core.model.location.Institution;
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

    @RequestMapping( method = RequestMethod.GET )
    public ResponseEntity<Response> get(){
        List<InstitutionDTO> institutions = locationService.getAllInstitutions()
                .stream()
                .map( institution ->  {
                    InstitutionDTO dto = new InstitutionDTO();
                    dto.setName( institution.getName() );
                    dto.setSlur( institution.getSlur() );
                    dto.setHint( institution.getHint() );
                    return dto;
                }).collect( Collectors.toList() );
        return new ResponseEntity<Response>( new Response<>( Meta.success(), institutions ), HttpStatus.OK );
    }

}
