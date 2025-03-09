package br.com.madr.resource;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestResponse;

import br.com.madr.dto.response.LivroDTOResponse;
import br.com.madr.exception.ApplicationServiceException;
import br.com.madr.service.LivroService;
import br.com.madr.utils.message.MessageResponse;
import br.com.madr.utils.message.MessageService;
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
@Tag(name = "livro", description = "Operações referentes aos livros")
@APIResponse(
        responseCode = "401",
        description = "Acesso não autorizado"
)
@APIResponse(
        responseCode = "403",
        description = "Você não tem permissão para acessar este recurso."
)
@APIResponse(
        responseCode = "500",
        description = "Tratamento de erro interno",
        content = @Content(
                mediaType = MediaType.APPLICATION_JSON,
                schema = @Schema(implementation = MessageService.class)
        )
)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LivroResource {

    private final LivroService livroService;

    public LivroResource(LivroService livroService) {
        this.livroService = livroService;
    }

    @GET
    @Path("/{id}")
    @Timeout(2000)
    @Retry(maxRetries = 4)
    @CircuitBreaker(requestVolumeThreshold = 10, failureRatio = 0.5, delay = 2500)
    @Operation(summary = "", description = "Consulta de registro por id")
    @Parameter(
            name = "id",
            description = "ID do livro",
            required = true,
            in = ParameterIn.PATH
    )
    @APIResponse(
            responseCode = "200",
            description = "Ok"
    )
    @APIResponse(
            responseCode = "400",
            description = "Parâmetro(s) inválido(s)"
    )
    public RestResponse<LivroDTOResponse> findById(@PathParam("id") Long id) throws ApplicationServiceException {
        LivroDTOResponse response = this.livroService.getById(id);
        return RestResponse.ResponseBuilder.
                <LivroDTOResponse>ok()
                .entity(response)
                .build();
    }

    @POST
    @Timeout(2000)
    @CircuitBreaker(requestVolumeThreshold = 10, failureRatio = 0.5, delay = 2500)
    @Operation(summary = "", description = "Inclusão de registro")
    @APIResponse(
            responseCode = "201",
            description = "Registro incluído."
    )
    @APIResponse(
            responseCode = "400",
            description = "Parâmetro(s) inválido(s)."
    )
    @APIResponse(
            responseCode = "409",
            description = "Conflito: A operação não pode ser concluída porque viola uma regra de negócio."
    )
    public RestResponse<LivroDTOResponse> register(LivroVO livroVO, @Context UriInfo uriInfo) throws ApplicationServiceException {
        LivroDTOResponse response = this.livroService.register(livroVO);
        return RestResponse.ResponseBuilder
                .<LivroDTOResponse>created(uriInfo.getAbsolutePath())
                .entity(response)
                .build();
    }

    @PATCH
    @Path("/{id}")
    @Timeout(2000)
    @CircuitBreaker(requestVolumeThreshold = 10, failureRatio = 0.5, delay = 2500)
    @Operation(summary = "", description = "Alteração de registro por id")
    @Parameter(
            name = "id",
            description = "ID do livro",
            required = true,
            in = ParameterIn.PATH
    )
    @APIResponse(
            responseCode = "204",
            description = "Registro alterado"
    )
    @APIResponse(
            responseCode = "400",
            description = "Parâmetro(s) inválido(s)"
    )
    @APIResponse(
            responseCode = "409",
            description = "Conflito: A operação não pode ser concluída porque viola uma regra de negócio."
    )
    public RestResponse<Void> update(@PathParam("id") Long id, LivroVO livroVO) throws ApplicationServiceException {
        this.livroService.update(id, livroVO);
        return RestResponse.noContent();
    }

    @DELETE
    @Path("/{id}")
    @Timeout(2000)
    @CircuitBreaker(requestVolumeThreshold = 10, failureRatio = 0.5, delay = 2500)
    @Operation(summary = "", description = "Exclusão de registro por id")
    @Parameter(
            name = "id",
            description = "ID do livro",
            required = true,
            in = ParameterIn.PATH
    )
    @APIResponse(
            responseCode = "204",
            description = "Registro excluído"
    )
    @APIResponse(
            responseCode = "400",
            description = "Parâmetro(s) inválido(s)"
    )
    public RestResponse<MessageResponse> deleteById(@PathParam("id") Long id) throws ApplicationServiceException {
        this.livroService.deleteById(id);
        return RestResponse.ok(new MessageResponse("Livro deletado no MADR"));
    }
}
