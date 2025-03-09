package br.com.madr.resource;

import org.jboss.resteasy.reactive.RestResponse;

import br.com.madr.dto.response.LivroDTOResponse;
import br.com.madr.exception.ApplicationServiceException;
import br.com.madr.service.LivroService;
import br.com.madr.utils.message.MessageResponse;
import br.com.madr.vo.LivroVO;
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

@Path("/livro")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LivroResource {
    private final LivroService livroService;

    public LivroResource(LivroService livroService) {
        this.livroService = livroService;
    }

    @GET
    @Path("/{id}")
    public RestResponse<LivroDTOResponse> findById(@PathParam("id") Long id) throws ApplicationServiceException{
        LivroDTOResponse response = this.livroService.getById(id);
        return RestResponse.ResponseBuilder.
                <LivroDTOResponse>ok()
                .entity(response)
                .build();
    }

    @POST
    public RestResponse<LivroDTOResponse> register(LivroVO livroVO, @Context UriInfo uriInfo) throws ApplicationServiceException{
        LivroDTOResponse response = this.livroService.register(livroVO);
        return RestResponse.ResponseBuilder
                .<LivroDTOResponse>created(uriInfo.getAbsolutePath())
                .entity(response)
                .build();
    }

    @PATCH
    @Path("/{id}")
    public RestResponse<Void> update(@PathParam("id") Long id, LivroVO livroVO) throws ApplicationServiceException{
        this.livroService.update(id, livroVO);
        return RestResponse.noContent();
    }

    @DELETE
    @Path("/{id}")
    public RestResponse<MessageResponse> deleteById(@PathParam("id") Long id) throws ApplicationServiceException{
        this.livroService.deleteById(id);
        return RestResponse.ok(new MessageResponse("Livro deletado no MADR"));
    }
}
