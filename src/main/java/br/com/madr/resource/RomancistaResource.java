package br.com.madr.resource;

import br.com.madr.dto.response.RomancistaDTOResponse;
import br.com.madr.exception.ApplicationServiceException;
import br.com.madr.service.RomancistaService;
import br.com.madr.vo.RomancistaVO;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/romancista")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RomancistaResource {

    private final RomancistaService romancistaService;

    public RomancistaResource(RomancistaService romancistaService) {
        this.romancistaService = romancistaService;
    }


    @POST
    public RestResponse<RomancistaDTOResponse> register(RomancistaVO romancistaVO, @Context UriInfo uriInfo) throws ApplicationServiceException {
        RomancistaDTOResponse response = this.romancistaService.register(romancistaVO);
        return RestResponse.ResponseBuilder
                .<RomancistaDTOResponse>created(uriInfo.getAbsolutePath())
                .entity(response)
                .build();

    }
}
