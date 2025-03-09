package br.com.madr.resource;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestResponse;

import br.com.madr.exception.ApplicationServiceException;
import br.com.madr.service.UserService;
import br.com.madr.utils.message.MessageService;
import br.com.madr.vo.UserVO;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;

@Path("/conta")
@Tag(name = "conta", description = "Operações referentes aos usuários")
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
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @POST
    @PermitAll
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
    public RestResponse<Void> register(UserVO userVO, @Context UriInfo uriInfo) throws ApplicationServiceException {
        this.userService.register(userVO);
        return RestResponse.created(uriInfo.getAbsolutePath());
    }

    @PUT
    @Timeout(2000)
    @CircuitBreaker(requestVolumeThreshold = 10, failureRatio = 0.5, delay = 2500)
    @Operation(summary = "", description = "Alteração de registro")
    @APIResponse(
            responseCode = "204",
            description = "Registro alterado"
    )
    @APIResponse(
            responseCode = "400",
            description = "Parâmetro(s) inválido(s)"
    )
    public RestResponse<Void> update(UserVO userVO) throws ApplicationServiceException {
        this.userService.update(userVO);
        return RestResponse.noContent();
    }

    @DELETE
    @Path("/{id}")
    @Timeout(2000)
    @CircuitBreaker(requestVolumeThreshold = 10, failureRatio = 0.5, delay = 2500)
    @Operation(summary = "", description = "Exclui uma conta por id")
    @Parameter(
            name = "id",
            description = "ID da conta",
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
    public RestResponse<Void> delete(@PathParam("id") Long id) throws ApplicationServiceException {
        this.userService.deleteById(id);
        return RestResponse.noContent();
    }
}
