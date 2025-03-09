package br.com.madr.resource;

import org.jboss.resteasy.reactive.RestResponse;

import br.com.madr.dto.response.RomancistaDTOResponse;
import br.com.madr.exception.ApplicationServiceException;
import br.com.madr.service.RomancistaService;
import br.com.madr.utils.message.MessageResponse;
import br.com.madr.vo.RomancistaVO;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;

@Path("/romancista")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RomancistaResource {

    private final RomancistaService romancistaService;

    public RomancistaResource(RomancistaService romancistaService) {
        this.romancistaService = romancistaService;
    }

    @GET
    @Path("/{id}")
    public RestResponse<RomancistaDTOResponse> getById(@PathParam("id") Long id) throws ApplicationServiceException {
        RomancistaDTOResponse response = this.romancistaService.getById(id);
        return RestResponse.ResponseBuilder
                .<RomancistaDTOResponse>ok()
                .entity(response)
                .build();
    }

    @POST
    public RestResponse<RomancistaDTOResponse> register(RomancistaVO romancistaVO, @Context UriInfo uriInfo) throws ApplicationServiceException {
        RomancistaDTOResponse response = this.romancistaService.register(romancistaVO);
        return RestResponse.ResponseBuilder
                .<RomancistaDTOResponse>created(uriInfo.getAbsolutePath())
                .entity(response)
                .build();
    }

    @PATCH
    @Path("/{id}")
    public RestResponse<Void> updatedPatch(@PathParam("id") Long id, RomancistaVO romancistaVO) throws ApplicationServiceException {
        this.romancistaService.updatePatch(id, romancistaVO);
        return RestResponse.noContent();
    }

    @DELETE
    @Path("/{id}")
    public RestResponse<MessageResponse> deleteById(@PathParam("id") Long id) throws ApplicationServiceException {
        this.romancistaService.deleteById(id);
        return RestResponse.ok(new MessageResponse("Romancista deletada no MADR"));
    }
}
