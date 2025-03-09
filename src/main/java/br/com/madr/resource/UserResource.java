package br.com.madr.resource;

import org.jboss.resteasy.reactive.RestResponse;

import br.com.madr.exception.ApplicationServiceException;
import br.com.madr.service.UserService;
import br.com.madr.vo.UserVO;
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
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @POST
    public RestResponse<Void> register(UserVO userVO, @Context UriInfo uriInfo) throws ApplicationServiceException {
        this.userService.register(userVO);
        return RestResponse.created(uriInfo.getAbsolutePath());
    }

    @PUT
    public RestResponse<Void> update(UserVO userVO) throws ApplicationServiceException{
        this.userService.update(userVO);
        return RestResponse.noContent();
    }

    @DELETE
    @Path("/{id}")
    public RestResponse<Void> delete(@PathParam("id") Long id) throws ApplicationServiceException {
        this.userService.deleteById(id);
        return RestResponse.noContent();
    }
}
